package za.ac.cput.views;

import za.ac.cput.entity.Role;
import za.ac.cput.entity.User;
import za.ac.cput.views.BookingViews.BookingForm;
import za.ac.cput.views.BookingViews.BookingDetail;
import za.ac.cput.views.BookingViews.RoomSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame implements ActionListener {

    // Panels for the layout
    private JPanel panelNorth, panelWest, panelEast;

    // North panel components
    private JLabel lblLogo;
    private JLabel lblHeader;
    private JButton btnLogout;

    // West panel components (buttons)
    private JButton btnBooking, btnBookingDetails, btnRooms, btnGuests, btnUserDetails;

    // Fonts for styling
    private Font headerFont = new Font("Arial", Font.BOLD, 36);
    private Font buttonFont = new Font("Arial", Font.BOLD, 14);

    // File path for the logo image
    private static final String LOGO_PATH = "src/main/resources/logo.png";
    private static final String BOOKING_PATH = "src/main/resources/booking.png";
    private static final String CHECKOUT_PATH = "src/main/resources/checkout.png";
    private static final String BOOKING_DETAILS_PATH = "src/main/resources/bookingDetails.png";
    private static final String GUESTS_PATH = "src/main/resources/guest.png";
    private static final String ROOMS_PATH = "src/main/resources/room.png";
    private static final String LOGOUT_PATH = "src/main/resources/logout.png";
    private static final String USER_DETAILS_PATH = "src/main/resources/User.png";

    private User loggedInUser;  // Store the logged-in user details

    public Dashboard(User user) {
        super("Booking Management Dashboard");
        this.loggedInUser = user;

        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelEast = new JPanel();

        lblLogo = new JLabel(scaleImage(LOGO_PATH, 120, 100));  // Scaled logo
        lblHeader = new JLabel("BOOKING MANAGEMENT", JLabel.CENTER);

        btnLogout = new JButton("LOG OUT");
        btnLogout.setIcon(scaleImage(LOGOUT_PATH, 35, 35));


        btnBooking = new JButton("BOOKING");
        btnBooking.setIcon(scaleImage(BOOKING_PATH, 50, 50));
        btnBooking.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBooking.setHorizontalTextPosition(SwingConstants.CENTER);

        btnBookingDetails = new JButton("BOOKING DETAILS");
        btnBookingDetails.setIcon(scaleImage(BOOKING_DETAILS_PATH, 50, 50));
        btnBookingDetails.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBookingDetails.setHorizontalTextPosition(SwingConstants.CENTER);

        btnRooms = new JButton("ROOMS");
        btnRooms.setIcon(scaleImage(ROOMS_PATH, 50, 50));
        btnRooms.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnRooms.setHorizontalTextPosition(SwingConstants.CENTER);

        btnGuests = new JButton("GUESTS");
        btnGuests.setIcon(scaleImage(GUESTS_PATH, 50, 50));
        btnGuests.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnGuests.setHorizontalTextPosition(SwingConstants.CENTER);


        btnUserDetails = new JButton("USER DETAILS");
        btnUserDetails.setIcon(scaleImage(USER_DETAILS_PATH, 50, 50));
        btnUserDetails.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnUserDetails.setHorizontalTextPosition(SwingConstants.CENTER);

        if (!isManager(loggedInUser)) {
            btnUserDetails.setVisible(false);
        }

        setGUI();
    }

    private boolean isManager(User user) {
        if (user.getRoles() == null) {
            return false;
        }
        for (Role role : user.getRoles()) {
            if (role.getRoleName().equalsIgnoreCase("MANAGER")) {
                return true;
            }
        }
        return false;
    }

    public void setGUI() {
        panelNorth.setLayout(new BorderLayout());
        panelNorth.setBackground(new Color(0, 51, 102));  // Dark blue
        panelNorth.setPreferredSize(new Dimension(0, 100));  // Match the height of the logo

        panelNorth.add(lblLogo, BorderLayout.WEST);
        lblHeader.setFont(headerFont);
        lblHeader.setForeground(Color.WHITE);
        panelNorth.add(lblHeader, BorderLayout.CENTER);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(buttonFont);
        panelNorth.add(btnLogout, BorderLayout.EAST);

        panelWest.setLayout(new GridLayout(7, 1, 10, 10));
        panelWest.setBackground(new Color(0, 51, 102));

        addButtonToPanel(panelWest, btnBooking);
        addButtonToPanel(panelWest, btnBookingDetails);
        addButtonToPanel(panelWest, btnRooms);
        addButtonToPanel(panelWest, btnGuests);
        addButtonToPanel(panelWest, btnUserDetails);

        panelEast.setBackground(Color.WHITE);

        // Add panels to the frame
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelWest, BorderLayout.WEST);
        this.add(panelEast, BorderLayout.CENTER);

        // Add action listeners to buttons
        btnBooking.addActionListener(this);
        btnBookingDetails.addActionListener(this);
        btnRooms.addActionListener(this);
        btnGuests.addActionListener(this);
        btnUserDetails.addActionListener(this);
        btnLogout.addActionListener(this);

        // Frame settings
        this.setSize(1200, 700);  // Set appropriate size
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    // Helper method to scale the image
    private ImageIcon scaleImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    // Helper method to add buttons with consistent styles to the panel
    private void addButtonToPanel(JPanel panel, JButton button) {
        button.setBackground(new Color(135, 206, 250));  // Sky blue
        button.setForeground(Color.BLACK);
        button.setFont(buttonFont);
        button.setFocusPainted(false);  // Removes the focus border
        panel.add(button);
    }

    // Method to load the RoomSearch panel into the central space
    public void displayRoomSearch() {
        panelEast.removeAll();
        RoomSearch roomSearch = new RoomSearch(this);
        panelEast.setLayout(new BorderLayout());
        panelEast.add(roomSearch.getContentPane(), BorderLayout.CENTER);
        panelEast.revalidate();
        panelEast.repaint();
    }

    private void displayBookingInfo(){
        panelEast.removeAll();
        BookingDetail bookingDetail = new BookingDetail();
        panelEast.setLayout(new BorderLayout());
        panelEast.add(bookingDetail.getContentPane(), BorderLayout.CENTER);
        panelEast.revalidate();
        panelEast.repaint();
    }

    private void displayUserDetails(){
        panelEast.removeAll();
        UserScreen userScreen = new UserScreen();
        panelEast.setLayout(new BorderLayout());
        panelEast.add(userScreen.getContentPane(), BorderLayout.CENTER);
        panelEast.revalidate();
        panelEast.repaint();
    }

    public void showBookingForm(String checkIn, String checkOut, String roomNumber, double price, String roomType) {
        panelEast.removeAll();
        BookingForm bookingView = new BookingForm(this, checkIn, checkOut, roomNumber, price, roomType);
        panelEast.setLayout(new BorderLayout());
        panelEast.add(bookingView.getContentPane(), BorderLayout.CENTER);
        panelEast.revalidate();
        panelEast.repaint();
    }


    private void displayGuestDetails() {
        panelEast.removeAll();
        GuestView guestDetails = new GuestView();
        panelEast.setLayout(new BorderLayout());
        panelEast.add(guestDetails.getContentPane(), BorderLayout.CENTER);
        panelEast.revalidate();
        panelEast.repaint();
    }

    private void displayRoom() {
        panelEast.removeAll();
        roomGui room = new roomGui();
        panelEast.setLayout(new BorderLayout());
        panelEast.add(room.getContentPane(), BorderLayout.CENTER);
        panelEast.revalidate();
        panelEast.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogout) {
            System.out.println("Logged out");

            // Clear the logged-in user data
            loggedInUser = null;

            // Redirect to the login screen
            new LoginScreen();

            // Close the current dashboard
            this.dispose();
        } else if (e.getSource() == btnBooking) {
            System.out.println("Booking button clicked");
            displayRoomSearch();
        }
        else if (e.getSource() == btnBookingDetails) {
            System.out.println("Booking Details button clicked");
            displayBookingInfo();
        }
        else if (e.getSource() == btnRooms) {
            System.out.println("Rooms button clicked");
            displayRoom();
        }
        else if (e.getSource() == btnGuests) {
            System.out.println("Guests button clicked");
            displayGuestDetails();
        }
        else if (e.getSource() == btnUserDetails) {
            System.out.println("User Details button clicked");
            displayUserDetails();
        }
    }

    public static void main(String[] args) {
        new Dashboard(new User());
    }
}
