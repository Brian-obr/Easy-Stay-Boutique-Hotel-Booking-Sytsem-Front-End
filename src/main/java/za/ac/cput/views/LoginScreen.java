package za.ac.cput.views;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import za.ac.cput.entity.User;
import za.ac.cput.factory.UserFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginScreen extends JFrame implements ActionListener {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().create();
    private JPanel contentPane;
    private JTextField userNameTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton, cancelButton;

    public LoginScreen() {
        super("Login Screen");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(lblLogin, gbc);

        JLabel lblEmail = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(lblEmail, gbc);

        userNameTextField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPane.add(userNameTextField, gbc);

        JLabel lblPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(lblPassword, gbc);

        passwordTextField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPane.add(passwordTextField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(loginButton, gbc);

        cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPane.add(cancelButton, gbc);

        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean validateUser(User user) {
        if (user.getUserName().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid username");
            userNameTextField.requestFocus();
            return false;
        } else if (user.getPassword().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a password");
            passwordTextField.requestFocus();
            return false;
        }
        return true;
    }

    private void sendData(User user) {
        if (!validateUser(user)) {
            return;
        }
        final String URL = "http://localhost:8080/easyStayHotel/auth/login";
        System.out.println("Sending data to URL: " + URL);

        String jsonLoginData = gson.toJson(user);
        System.out.println("Login data being sent: " + jsonLoginData);

        RequestBody body = RequestBody.create(jsonLoginData, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                User authenticatedUser = gson.fromJson(responseBody, User.class);
                JOptionPane.showMessageDialog(this, "Login Successful");
                System.out.println(authenticatedUser);
                new Dashboard(authenticatedUser).setVisible(true);
                dispose();
            } else {
                String responseBody = response.body().string();
                JOptionPane.showMessageDialog(this, "Login failed: " + responseBody);
                System.out.println(response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            User user = UserFactory.buildUserLogin(userNameTextField.getText(), new String(passwordTextField.getPassword()));
            sendData(user);
        } else if (e.getSource() == cancelButton) {
            clearTextFields();
        }
    }

    private void clearTextFields() {
        userNameTextField.setText("");
        passwordTextField.setText("");
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}
