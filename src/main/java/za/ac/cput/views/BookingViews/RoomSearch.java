package za.ac.cput.views.BookingViews;

import okhttp3.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.util.DateLabelFormatter;
import za.ac.cput.views.Dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class RoomSearch extends JFrame implements ActionListener {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "http://localhost:8080/easyStayHotel/room";

    private Dashboard dashboard;

    private JPanel panelNorth, panelCenter;

    private JLabel lblCheckIn, lblCheckOut;
    private JDatePickerImpl datePickerCheckIn, datePickerCheckOut;
    private JButton btnSearch;

    private JTable tableRooms;
    private JScrollPane scrollPane;
    private String[] columnNames = {"Room Number", "Price", "Room Type", "Action"};
    private DefaultTableModel tableModel;

    public RoomSearch(Dashboard dashboard) {
        super("Room Search");

        this.dashboard = dashboard; // Assign the dashboard reference

        panelNorth = new JPanel();
        panelCenter = new JPanel();

        lblCheckIn = new JLabel("CHECK IN");
        lblCheckOut = new JLabel("CHECK OUT");
        datePickerCheckIn = createDatePicker();
        datePickerCheckOut = createDatePicker();
        btnSearch = new JButton("SEARCH");

        setGUI();
    }

    private void setGUI() {
        panelNorth.setLayout(new GridLayout(3, 2, 10, 10));
        panelNorth.setBorder(BorderFactory.createTitledBorder("ROOM SEARCH"));
        panelNorth.add(lblCheckIn);
        panelNorth.add(datePickerCheckIn);
        panelNorth.add(lblCheckOut);
        panelNorth.add(datePickerCheckOut);
        panelNorth.add(new JLabel(""));  // Placeholder
        panelNorth.add(btnSearch);

        tableModel = new DefaultTableModel(columnNames, 0);
        tableRooms = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Make the action column editable
            }
        };

        tableRooms.setRowHeight(30); // Set row height
        tableRooms.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the table

        TableColumn actionColumn = tableRooms.getColumnModel().getColumn(3);
        actionColumn.setCellRenderer(new ButtonRenderer());
        actionColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        // Set column widths
        tableRooms.getColumnModel().getColumn(0).setPreferredWidth(100); // Room Number
        tableRooms.getColumnModel().getColumn(1).setPreferredWidth(100); // Price
        tableRooms.getColumnModel().getColumn(2).setPreferredWidth(150); // Room Type
        tableRooms.getColumnModel().getColumn(3).setPreferredWidth(120); // Action

        scrollPane = new JScrollPane(tableRooms);
        tableRooms.setFillsViewportHeight(true);

        panelCenter.setLayout(new BorderLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("AVAILABLE ROOMS"));
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);

        btnSearch.addActionListener(this);

        // Set frame properties
//        this.setSize(800, 600);  // Set appropriate size
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocationRelativeTo(null);
    }

    private JDatePickerImpl createDatePicker() {
        SqlDateModel model = new SqlDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            searchAvailableRooms();
        }
    }

    private void searchAvailableRooms() {
        try {
            // Fetch dates from the date pickers
            LocalDate checkInDate = ((Date) datePickerCheckIn.getModel().getValue()).toLocalDate();
            LocalDate checkOutDate = ((Date) datePickerCheckOut.getModel().getValue()).toLocalDate();

            // Validate the dates
            if (checkInDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Check-in date cannot be in the past.");
                return;
            }

            if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.");
                return;
            }

            // Format dates
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            String formattedCheckInDate = checkInDate.format(formatter);
            String formattedCheckOutDate = checkOutDate.format(formatter);

            // API request to fetch available rooms
            String url = BASE_URL + "/available?checkInDate=" + formattedCheckInDate + "&checkOutDate=" + formattedCheckOutDate;
            String jsonResponse = run(url);

            // Parse JSON response
            JSONArray rooms = new JSONArray(jsonResponse);

            // Clear previous table data
            tableModel.setRowCount(0);

            // Populate the table with new available rooms
            for (int i = 0; i < rooms.length(); i++) {
                JSONObject room = rooms.getJSONObject(i);
                Long roomNumber = room.getLong("roomNumber");
                Double price = room.getDouble("pricePerNight");
                String roomType = room.getString("roomType");

                // Add row to the table
                tableModel.addRow(new Object[]{roomNumber, price, roomType, "Select Room"});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching rooms: " + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // OkHttp call method
    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    // Custom renderer for the button in the table
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Select Room" : value.toString());
            return this;
        }
    }

    // Custom editor for the button in the table
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private Long roomNumber;
        private double price;
        private String roomType;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Select Room");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    openBookingForm(roomNumber, price, roomType);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            roomNumber = (Long) table.getValueAt(row, 0);
            price = (Double) table.getValueAt(row, 1);
            roomType = (String) table.getValueAt(row, 2);
            return button;
        }

        private void openBookingForm(Long roomNumber, double price, String roomType) {
            LocalDate checkInDate = ((Date) datePickerCheckIn.getModel().getValue()).toLocalDate();
            LocalDate checkOutDate = ((Date) datePickerCheckOut.getModel().getValue()).toLocalDate();

            String checkIn = checkInDate.toString();
            String checkOut = checkOutDate.toString();

            // Remove current panel and show BookingForm in Dashboard
            dashboard.showBookingForm(checkIn, checkOut, roomNumber.toString(), price, roomType);
        }
    }
}
