package za.ac.cput.views.BookingViews;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;

// This class displays booking information using a JTable in a JFrame
public class BookingDetail extends JFrame {

    private static OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "http://localhost:8080/easyStayHotel/booking";

    private JTable bookingTable;
    private DefaultTableModel tableModel;

    // Column names for the booking table
    String[] columnNames = {"Booking ID", "Booking Date", "Check-In Date", "Check-Out Date", "Total Amount", "GuestID", "Room Number", "Action"};

    // Constructor for setting up the JFrame and JTable
    public BookingDetail() {
        setTitle("Booking Information");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup the table model with column names
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);

        // Add custom renderer and editor for the "Cancel Booking" button
        bookingTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bookingTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Add the table to the scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch the booking data from the backend
        BookingList();
    }

    // Method for making a GET request and retrieving the response from the server
    private static String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    // Method for sending a DELETE request to cancel the booking
    private static String checkOut(long bookingId) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/cancel/" + bookingId)
                .delete()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    // Method to fetch booking data from the backend and populate the JTable
    private void BookingList() {
        try {
            String url = BASE_URL + "/all";
            String jsonResponse = run(url);

            // First, check if the response is a valid JSON array
            if (jsonResponse.trim().startsWith("[")) {
                JSONArray bookings = new JSONArray(jsonResponse);
                tableModel.setRowCount(0); // Clear previous table data

                for (int i = 0; i < bookings.length(); i++) {
                    JSONObject booking = bookings.getJSONObject(i);

                    // Extract data for each booking record
                    long bookingId = booking.getLong("bookingId");
                    String bookingDate = booking.getString("bookingDate");
                    String checkInDate = booking.getString("checkInDate");
                    String checkOutDate = booking.getString("checkOutDate");
                    double totalAmount = booking.getDouble("totalAmount");
                    long guestId = booking.getJSONObject("guest").getLong("guestId");
                    long roomNumber = booking.getJSONObject("room").getLong("roomNumber");

                    // Add a new row to the JTable
                    tableModel.addRow(new Object[]{bookingId, bookingDate, checkInDate, checkOutDate, totalAmount, guestId, roomNumber, "CheckOut"});
                }
            } else {
                throw new Exception("Expected JSON array but got: " + jsonResponse);
            }
        } catch (Exception ex) {
            System.out.println("Error fetching bookings: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error fetching bookings: " + ex.getMessage());
        }
    }

    // Renderer for displaying the Cancel Booking button
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "CheckOut" : value.toString());
            return this;
        }
    }

    // Editor for handling the click event of the Cancel Booking button
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private Long bookingId;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("CheckOut");
            button.addActionListener(e -> cancelBookingAction());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            bookingId = (long) table.getModel().getValueAt(row, 0);
            return button;
        }

        // Action performed when Cancel Booking button is clicked
        private void cancelBookingAction() {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to CheckOut this booking?", "Check Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String response = checkOut(bookingId);
                    JOptionPane.showMessageDialog(null, "Booking Check Out successfully.");
                    BookingList(); // Refresh the booking list
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error Check Out: " + ex.getMessage());
                }
            }
        }
    }
}
