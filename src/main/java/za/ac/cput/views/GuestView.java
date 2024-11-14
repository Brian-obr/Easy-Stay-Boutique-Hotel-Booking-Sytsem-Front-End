package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import za.ac.cput.entity.Guest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GuestView extends JFrame {

    private JTable guestTable;
    private DefaultTableModel tableModel;

    private static final String DATE_PATTERN = "yyyy-MM-dd"; // Adjust if needed
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                    LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(DATE_PATTERN)))
            .create();

    private static OkHttpClient client = new OkHttpClient();

    public GuestView() {
        super("Guest Information");

        // Initialize the table model
        tableModel = new DefaultTableModel();

        // Add columns to the table model
        tableModel.addColumn("Guest ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Date of Birth");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");
        tableModel.addColumn("Street Address");
        // tableModel.addColumn("Suburb");
        tableModel.addColumn("City");
        tableModel.addColumn("Postal Code");
        tableModel.addColumn("Country");

        // Initialize the table with the table model
        guestTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(guestTable);

        // Set layout and add components
        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);

        // Load guest details
        loadGuestDetails();

        // Frame properties
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(800, 600);
//        setVisible(true);
    }

    private void loadGuestDetails() {
        String url = "http://localhost:8080/easyStayHotel/guests/all";
        try {
            String jsonResponse = get(url);
            Guest[] guests = GSON.fromJson(jsonResponse, Guest[].class);

            // Populate the table model
            for (Guest guest : guests) {
                Object[] rowData = {
                        guest.getGuestId(),
                        guest.getGuestFirstName(),
                        guest.getGuestLastName(),
                        guest.getGuestDateOfBirth(),
                        guest.getGuestGender(),
                        guest.getPhoneNumber(),
                        guest.getEmail(),
                        guest.getStreetAddress(),
                        // guest.getSuburb(),
                        guest.getCity(),
                        guest.getPostalCode(),
                        guest.getCountry()
                };
                tableModel.addRow(rowData);
            }
        } catch (IOException e) {
            System.out.println("Error loading guest details: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading guest details: " + e.getMessage());
        }
    }

    public String get(final String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

}
