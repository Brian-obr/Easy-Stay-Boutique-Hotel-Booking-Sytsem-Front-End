package za.ac.cput.views;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import za.ac.cput.entity.Room;
import za.ac.cput.entity.enums.RoomType;
import za.ac.cput.factory.RoomFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class roomGui extends JFrame implements ActionListener {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().create();

    private JPanel tablePanel, buttonPanel, formPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton deleteButton, addButton;
    private JTextField roomNumberField, priceField;
    private JComboBox<RoomType> roomTypeComboBox;

    public roomGui() {
        super("Room Management");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Room Number");
        tableModel.addColumn("Room Type");
        tableModel.addColumn("Price");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 400));

        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                deleteButton.setEnabled(true);
            } else {
                deleteButton.setEnabled(false);
            }
        });

        populateTable();

        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(this);

        addButton = new JButton("Add Room");
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(addButton);

        tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberField = new JTextField(15);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeComboBox = new JComboBox<>(RoomType.values());

        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField(15);

        formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(roomNumberLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(roomNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(roomTypeLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(roomTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(addButton, gbc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, formPanel);
        splitPane.setResizeWeight(0.5);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);

//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 500);
//        setLocationRelativeTo(null);
//        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Ensure roomNumber is a Long
                Long roomNumber = (Long) tableModel.getValueAt(selectedRow, 0);
                handleDeleteRoom((roomNumber));
                tableModel.removeRow(selectedRow);
                showAlert("Success", "Room deleted successfully!", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == addButton) {
            handleAddRoom();
        }
    }

    private void handleAddRoom() {
        String roomNumberStr = roomNumberField.getText();
        RoomType roomType = (RoomType) roomTypeComboBox.getSelectedItem();
        String priceStr = priceField.getText();

        if (roomNumberStr.isEmpty() || priceStr.isEmpty()) {
            showAlert("Error", "Room Number and Price cannot be empty.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        long roomNumber = Long.parseLong(roomNumberStr);
        double pricePerNight = Double.parseDouble(priceStr);

        Room newRoom = RoomFactory.buildRoom(roomNumber, pricePerNight,roomType);

        saveRoom(newRoom);

        tableModel.addRow(new Object[]{roomNumber, roomType.toString(), pricePerNight});

        roomNumberField.setText("");
        priceField.setText("");
        roomTypeComboBox.setSelectedIndex(0);
    }

    private void populateTable() {
        try {
            final String URL = "http://localhost:8080/easyStayHotel/room/all";
            String response = run(URL);

            if (response == null || response.isEmpty()) {
                showAlert("Error", "No rooms found.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Room[] rooms = gson.fromJson(response, Room[].class);

            tableModel.setRowCount(0);

            for (Room room : rooms) {
                tableModel.addRow(new Object[]{room.getRoomNumber(), room.getRoomType().toString(), room.getPricePerNight()});
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to fetch rooms.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteRoom(Long roomNumber) {
        String url = "http://localhost:8080/easyStayHotel/room/delete/" + roomNumber;
        System.out.println("Sending DELETE request to URL: " + url);

        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String responseBody = response.body().string();
                throw new IOException("Unexpected code " + response + " with body: " + responseBody);
            }

            System.out.println("Room with number " + roomNumber + " deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error sending data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveRoom(Room room) {
        try {
            final String URL = "http://localhost:8080/easyStayHotel/room/save";
            String jsonRoom = gson.toJson(room);
            RequestBody body = RequestBody.create(jsonRoom, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder().url(URL).post(body).build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response.body().string());
                }
            }

        } catch (IOException e) {
            showAlert("Error", "Failed to save room: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String run(String URL) throws IOException {
        Request request = new Request.Builder().url(URL).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.body().string());
            }
            return response.body().string();
        }
    }

    private void showAlert(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

}


