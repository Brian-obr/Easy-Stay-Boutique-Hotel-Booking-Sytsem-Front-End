package za.ac.cput.views.BookingViews;

import com.google.gson.Gson;
import okhttp3.*;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.DateComponentFormatter;
import za.ac.cput.entity.Booking;
import za.ac.cput.entity.Guest;
import za.ac.cput.entity.Room;
import za.ac.cput.entity.enums.RoomType;
import za.ac.cput.factory.BookingFactory;
import za.ac.cput.factory.GuestFactory;
import za.ac.cput.util.GsonFactory;
import za.ac.cput.views.Dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

public class BookingForm extends JFrame implements ActionListener {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = GsonFactory.createGson();
    private static final String BASE_URL = "http://localhost:8080/easyStayHotel/booking";

    private Dashboard dashboard;

    // Panels
    private JPanel panelNorth, panelCenter, panelSouth, panelEast, panelWest;

//    private JLabel lblBookingDetails, lblGuestDetails, lblRoomDetails;
    // Booking Details Labels
    private JLabel lblCheckIn, lblCheckOut, lblDuration, lblTotalAmount, rsCheckIn, rsCheckOut, rsDuration, rsTotalAmount;
    private JLabel lblRoomNumber, lblPricePerNight, lblRoomType, rsRoomNumber, rsPricePerNight, rsRoomType;

    // Guest Details Labels and Text Fields
    private JLabel lblFirstName, lblLastName, lblDateOfBirth, lblGender;
    private JLabel lblStreetAddress, lblSuburb, lblCity, lblPostalCode, lblCountry, lblPhoneNumber, lblEmail;

    private JTextField txtFirstName, txtLastName, txtStreetAddress;
    private JTextField txtSuburb, txtCity, txtPostalCode, txtCountry, txtPhoneNumber, txtEmail;
    private JComboBox<String> cmbGender;
    private JDatePickerImpl datePicker;

    // Buttons
    private JButton btnConfirm, btnCancel;

    // Variables to hold room and booking details from previous actions
    private String checkInDate, checkOutDate, roomNumber, roomType;
    private double pricePerNight;
    private int duration;  // Duration in nights

    public BookingForm(Dashboard dashboard, String checkInDate, String checkOutDate, String roomNumber, double pricePerNight, String roomType) {
        super("Booking Details");
        this.dashboard = dashboard;
        // Set the data from the RoomSearch and booking selection
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.roomType = roomType;
        this.duration = calculateDuration(checkInDate, checkOutDate);  // Assuming a method to calculate duration

        // Initialize components
        setGui();
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void setGui() {
        // Panel layout
        panelNorth = new JPanel(new GridLayout(1, 2));

        panelWest = new JPanel(new GridLayout(4, 2));
        panelWest.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        lblCheckIn = new JLabel("Check In:");
        rsCheckIn = new JLabel(checkInDate);
        lblCheckOut = new JLabel("Check Out:");
        rsCheckOut = new JLabel(checkOutDate);
        lblDuration = new JLabel("Duration (Nights): ");
        rsDuration = new JLabel(duration + "");
        lblTotalAmount = new JLabel("Total Amount: ");
        rsTotalAmount = new JLabel((duration * pricePerNight) + "");

        panelEast = new JPanel(new GridLayout(3, 1));
        panelEast.setBorder(BorderFactory.createTitledBorder("Room Details"));
        lblRoomNumber = new JLabel("Room Number:");
        rsRoomNumber = new JLabel(roomNumber);
        lblPricePerNight = new JLabel("Price Per Night: ");
        rsPricePerNight = new JLabel(pricePerNight + "");
        lblRoomType = new JLabel("Room Type: ");
        rsRoomType = new JLabel(roomType);


        panelCenter = new JPanel(new GridLayout(11, 2));
        panelSouth = new JPanel();




        // Room details
//        lblRoomDetails = new JLabel("Room Details");


//        panelWest.add(lblBookingDetails);
        panelWest.add(lblCheckIn);
        panelWest.add(rsCheckIn);
        panelWest.add(lblCheckOut);
        panelWest.add(rsCheckOut);
        panelWest.add(lblDuration);
        panelWest.add(rsDuration);
        panelWest.add(lblTotalAmount);
        panelWest.add(rsTotalAmount);

//        panelEast.add(lblRoomDetails);
        panelEast.add(lblRoomNumber);
        panelEast.add(rsRoomNumber);
        panelEast.add(lblPricePerNight);
        panelEast.add(rsPricePerNight);
        panelEast.add(lblRoomType);
        panelEast.add(rsRoomType);

        panelNorth.add(panelWest);
        panelNorth.add(panelEast);

        // Guest details labels and text fields
        lblFirstName = new JLabel("First Name:");
        lblLastName = new JLabel("Last Name:");
        lblDateOfBirth = new JLabel("Date of Birth:");
        lblGender = new JLabel("Gender:");
        lblStreetAddress = new JLabel("Street Address:");
        lblSuburb = new JLabel("Suburb:");
        lblCity = new JLabel("City:");
        lblPostalCode = new JLabel("Postal Code:");
        lblCountry = new JLabel("Country:");
        lblPhoneNumber = new JLabel("Phone Number:");
        lblEmail = new JLabel("Email:");

        txtFirstName = new JTextField(20);
        txtLastName = new JTextField(20);

        // Configure JDatePicker for date of birth
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        txtStreetAddress = new JTextField();
        txtSuburb = new JTextField();
        txtCity = new JTextField();
        txtPostalCode = new JTextField();
        txtCountry = new JTextField();
        txtPhoneNumber = new JTextField();
        txtEmail = new JTextField();

        // Add guest details to center panel
        panelCenter.setBorder(BorderFactory.createTitledBorder("Guest Details"));
        panelCenter.add(lblFirstName);
        panelCenter.add(txtFirstName);
        panelCenter.add(lblLastName);
        panelCenter.add(txtLastName);
        panelCenter.add(lblDateOfBirth);
        panelCenter.add(datePicker);
        panelCenter.add(lblGender);
        panelCenter.add(cmbGender);
        panelCenter.add(lblStreetAddress);
        panelCenter.add(txtStreetAddress);
        panelCenter.add(lblSuburb);
        panelCenter.add(txtSuburb);
        panelCenter.add(lblCity);
        panelCenter.add(txtCity);
        panelCenter.add(lblPostalCode);
        panelCenter.add(txtPostalCode);
        panelCenter.add(lblCountry);
        panelCenter.add(txtCountry);
        panelCenter.add(lblPhoneNumber);
        panelCenter.add(txtPhoneNumber);
        panelCenter.add(lblEmail);
        panelCenter.add(txtEmail);

        // Buttons
        btnConfirm = new JButton("Confirm");
        btnCancel = new JButton("Cancel");
        btnConfirm.addActionListener(this);
        btnCancel.addActionListener(this);

        // Add buttons to south panel
        panelSouth.add(btnCancel);
        panelSouth.add(btnConfirm);

        // Add panels to the frame
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
    }

    private int calculateDuration(String checkIn, String checkOut) {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        return (int) java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirm) {
            confirmBooking();

        } else if (e.getSource() == btnCancel) {
            JOptionPane.showMessageDialog(this, "Booking cancelled");
            dispose();
            dashboard.displayRoomSearch();// Close window
        }
    }

    private void confirmBooking() {
        try {
            String url = BASE_URL + "/create";

            // Convert date from datePicker to LocalDate
            java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a valid date of birth.");
                return;
            }
            LocalDate dateOfBirth = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Create Guest object
            Guest guest = GuestFactory.buildGuestWithoutId(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    dateOfBirth,
                    cmbGender.getSelectedItem().toString(),
                    txtStreetAddress.getText(),
                    txtSuburb.getText(),
                    txtCity.getText(),
                    txtPostalCode.getText(),
                    txtCountry.getText(),
                    txtPhoneNumber.getText(),
                    txtEmail.getText()
            );

            // Convert roomType string to RoomType enum
            RoomType roomTypeEnum = RoomType.valueOf(roomType.toUpperCase());

            // Create Room object
            Room room = new Room.Builder()
                    .setRoomNumber(Long.parseLong(roomNumber))
                    .setPricePerNight(pricePerNight)
                    .setRoomType(roomTypeEnum)
                    .build();

            // Convert check-in and check-out dates to LocalDate
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);

            // Create Booking object using the BookingFactory
            Booking booking = BookingFactory.buildBookingWithoutId(checkIn, checkOut, duration * pricePerNight, guest, room);

            String jsonString = gson.toJson(booking);

            RequestBody body = RequestBody.create(JSON, jsonString);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            // Execute request and handle response
            Response response = client.newCall(request).execute();

//            dashboard.displayRoomSearch();

            if (response.isSuccessful()) {
                dispose();
                JOptionPane.showMessageDialog(this, "Booking created successfully");
                dashboard.displayRoomSearch();
            } else {
                System.out.println(response.body().string());
                JOptionPane.showMessageDialog(this, "Failed to create booking: " + response.message());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to create booking due to an IO error");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid data: " + ex.getMessage());
        }
    }

}

