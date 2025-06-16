package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class creates the graphical user interface for user registration.
// It allows new users to create an account for QuickChatApp.
public class RegistrationFrontEnd extends JFrame implements ActionListener {

    //  Color Palette 
    private static final Color WHATSAPP_GREEN_PRIMARY = new Color(7, 94, 84); // Dark header/accent green
    private static final Color WHATSAPP_GREEN_ACCENT = new Color(37, 211, 102); // Lighter button/status green
    private static final Color WHATSAPP_BACKGROUND = new Color(236, 229, 221); // Light chat background
    private static final Color MODERN_TEXT_DARK = new Color(33, 33, 33);
    private static final Color MODERN_TEXT_LIGHT = new Color(97, 97, 97);
    private static final Color FIELD_BACKGROUND = Color.WHITE; // Clean white for inputs
    private static final Color ERROR_RED = new Color(220, 50, 50);

    //  Fonts 
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 28);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Font STATUS_FONT = new Font("SansSerif", Font.ITALIC, 14);

    //  GUI Components 
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton registerButton;
    private JButton backToLoginButton;
    private JLabel statusLabel;

    // Logic manager
    private Part1 accountManager;

    public RegistrationFrontEnd() {
        // Initialize logic manager
        accountManager = new Part1();

        //  Frame Setup 
        setTitle("QuickChatApp - Register");
        setSize(500, 720); // Adjusted size for modern look
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLocationRelativeTo(null); // Center on screen
        setResizable(false); // Fixed size for consistent look

        // Set overall background
        getContentPane().setBackground(WHATSAPP_BACKGROUND);
        setLayout(new BorderLayout(20, 20)); // Padding around main content

        //  Title Panel 
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(WHATSAPP_GREEN_PRIMARY); // Header background
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); // Vertical padding
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Bottom padding for content
        JLabel titleLabel = new JLabel("Create Your QuickChatApp Account");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE); // White text on dark green
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        //  Input Fields Panel 
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(WHATSAPP_BACKGROUND);
        inputPanel.setLayout(new GridBagLayout()); // Using GridBagLayout for flexible alignment and spacing
        inputPanel.setBorder(new EmptyBorder(0, 30, 0, 30)); // Horizontal padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally

        // Username
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(LABEL_FONT);
        userLabel.setForeground(MODERN_TEXT_DARK);
        inputPanel.add(userLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        usernameField = new JTextField(20);
        usernameField.setFont(INPUT_FONT);
        usernameField.setBackground(FIELD_BACKGROUND);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1), // Subtle border
            new EmptyBorder(8, 10, 8, 10) // Internal padding
        ));
        inputPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(LABEL_FONT);
        passLabel.setForeground(MODERN_TEXT_DARK);
        inputPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        passwordField = new JPasswordField(20);
        passwordField.setFont(INPUT_FONT);
        passwordField.setBackground(FIELD_BACKGROUND);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        inputPanel.add(passwordField, gbc);

        // First Name
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        JLabel firstLabel = new JLabel("First Name:");
        firstLabel.setFont(LABEL_FONT);
        firstLabel.setForeground(MODERN_TEXT_DARK);
        inputPanel.add(firstLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        firstNameField = new JTextField(20);
        firstNameField.setFont(INPUT_FONT);
        firstNameField.setBackground(FIELD_BACKGROUND);
        firstNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        inputPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        JLabel lastLabel = new JLabel("Last Name:");
        lastLabel.setFont(LABEL_FONT);
        lastLabel.setForeground(MODERN_TEXT_DARK);
        inputPanel.add(lastLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        lastNameField = new JTextField(20);
        lastNameField.setFont(INPUT_FONT);
        lastNameField.setBackground(FIELD_BACKGROUND);
        lastNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        inputPanel.add(lastNameField, gbc);

        add(inputPanel, BorderLayout.CENTER);

        //  Status Label & Buttons Panel 
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(WHATSAPP_BACKGROUND);
        bottomPanel.setBorder(new EmptyBorder(10, 30, 20, 30));

        statusLabel = new JLabel("Fill in your details to register.", SwingConstants.CENTER);
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(MODERN_TEXT_LIGHT);
        bottomPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Spacing between buttons
        buttonPanel.setBackground(WHATSAPP_BACKGROUND);

        registerButton = new JButton("Register");
        registerButton.setFont(BUTTON_FONT);
        registerButton.setBackground(WHATSAPP_GREEN_ACCENT);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker(), 1),
            new EmptyBorder(10, 25, 10, 25) // Padding for button size
        ));
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);

        backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setFont(BUTTON_FONT.deriveFont(Font.PLAIN, 14f)); // Slightly smaller font
        backToLoginButton.setBackground(null); // Transparent background
        backToLoginButton.setForeground(WHATSAPP_GREEN_PRIMARY); // Text color
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Padding only
        backToLoginButton.addActionListener(this);
        buttonPanel.add(backToLoginButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Handles button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();

            // Input validation
            if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                statusLabel.setText("All fields are required for registration.");
                statusLabel.setForeground(ERROR_RED);
                return;
            }

            // Attempt to register the user
            if (accountManager.registerUser(username, password, firstName, lastName)) {
                statusLabel.setText("Registration successful! You can now log in.");
                statusLabel.setForeground(WHATSAPP_GREEN_ACCENT);
                // Clear fields after successful registration
                usernameField.setText("");
                passwordField.setText("");
                firstNameField.setText("");
                lastNameField.setText("");
            } else {
                // Specific error messages are printed to console by Part1 methods.
                statusLabel.setText("Registration failed. Please check criteria (console for details).");
                statusLabel.setForeground(ERROR_RED);
            }
        } else if (e.getSource() == backToLoginButton) {
            // Close this registration window and open the login window
            LoginFrontEnd loginForm = new LoginFrontEnd();
            loginForm.setVisible(true);
            this.dispose(); // Close current window
        }
    }
}
