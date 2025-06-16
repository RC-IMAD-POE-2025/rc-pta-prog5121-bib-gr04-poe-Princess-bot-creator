package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class provides the graphical user interface for user login.
// Users can enter their credentials to access the chat application.
public class LoginFrontEnd extends JFrame implements ActionListener {

    // Color Palette
    private static final Color WHATSAPP_GREEN_PRIMARY = new Color(7, 94, 84); // Dark header/accent green
    private static final Color WHATSAPP_GREEN_ACCENT = new Color(37, 211, 102); // Lighter button/status green
    private static final Color WHATSAPP_BACKGROUND = new Color(236, 229, 221); // Light chat background
    private static final Color MODERN_TEXT_DARK = new Color(33, 33, 33);
    private static final Color MODERN_TEXT_LIGHT = new Color(97, 97, 97);
    private static final Color FIELD_BACKGROUND = Color.WHITE; // Clean white for inputs
    private static final Color ERROR_RED = new Color(220, 50, 50);

    // Fonts
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 32); // Larger title
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Font STATUS_FONT = new Font("SansSerif", Font.ITALIC, 14);

    // GUI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerNowButton; // Button to go to registration
    private JLabel statusLabel;

    // Logic manager
    private Part1 accountManager;

    public LoginFrontEnd() {
        // Initialize logic manager
        accountManager = new Part1();

        // Frame Setup
        setTitle("QuickChatApp - Login");
        setSize(900, 500); // Adjusted size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app on close
        setLocationRelativeTo(null); // Center on screen
        setResizable(false); // Fixed size for consistent look

        // Set overall background
        getContentPane().setBackground(WHATSAPP_BACKGROUND);
        setLayout(new BorderLayout(20, 20)); // Padding around main content

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(WHATSAPP_GREEN_PRIMARY); // Header background
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 25)); // Vertical padding
        titlePanel.setBorder(new EmptyBorder(0, 0, 25, 0)); // Bottom padding for content
        JLabel titleLabel = new JLabel("Welcome to QuickChatApp!");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE); // White text on dark green
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Input Fields Panel
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
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(8, 10, 8, 10)
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

        add(inputPanel, BorderLayout.CENTER);

        // Status Label & Buttons Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(15, 15)); // Increased spacing
        bottomPanel.setBackground(WHATSAPP_BACKGROUND);
        bottomPanel.setBorder(new EmptyBorder(10, 30, 20, 30));

        statusLabel = new JLabel("Enter your credentials to log in.", SwingConstants.CENTER);
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(MODERN_TEXT_LIGHT);
        bottomPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Spacing between buttons
        buttonPanel.setBackground(WHATSAPP_BACKGROUND);

        loginButton = new JButton("Login");
        loginButton.setFont(BUTTON_FONT);
        loginButton.setBackground(WHATSAPP_GREEN_ACCENT);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker(), 1),
            new EmptyBorder(10, 30, 10, 30) // Padding for button size
        ));
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        registerNowButton = new JButton("Register Now!");
        registerNowButton.setFont(BUTTON_FONT.deriveFont(Font.PLAIN, 14f)); // Slightly smaller font
        registerNowButton.setBackground(null); // Transparent background
        registerNowButton.setForeground(WHATSAPP_GREEN_PRIMARY); // Text color
        registerNowButton.setFocusPainted(false);
        registerNowButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding only
        registerNowButton.addActionListener(this);
        buttonPanel.add(registerNowButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Handles button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Attempt to log in the user
            Part1.User loggedInUser = accountManager.loginUser(username, password);
            if (loggedInUser != null) {
                statusLabel.setText("Login successful! Welcome, " + loggedInUser.getFirstName() + "!");
                statusLabel.setForeground(WHATSAPP_GREEN_ACCENT);
                // Open the main chat window and pass the logged-in user
                MessageFrontEnd chatForm = new MessageFrontEnd(loggedInUser);
                chatForm.setVisible(true);
                this.dispose(); // Close current login window
            } else {
                statusLabel.setText("Login failed. Invalid username or password.");
                statusLabel.setForeground(ERROR_RED);
            }
        } else if (e.getSource() == registerNowButton) {
            // Open the registration window
            RegistrationFrontEnd registerForm = new RegistrationFrontEnd();
            registerForm.setVisible(true);
            this.dispose(); // Close current login window
        }
    }
}
