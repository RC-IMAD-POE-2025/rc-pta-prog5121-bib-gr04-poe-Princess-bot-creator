package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegistrationFrontEnd extends JFrame implements ActionListener {

    // --- UI Styling ---
    private static final Color WHATSAPP_GREEN_PRIMARY = new Color(7, 94, 84);
    private static final Color WHATSAPP_GREEN_ACCENT = new Color(37, 211, 102);
    private static final Color WHATSAPP_BACKGROUND = new Color(236, 229, 221);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

    // --- UI Components ---
    private JTextField firstNameField, lastNameField, usernameField, cellphoneField;
    private JPasswordField passwordField;
    private JButton registerButton;
    
    // --- Data Management ---
    private ArrayList<Registration> registeredUsers;

    public RegistrationFrontEnd(ArrayList<Registration> userList) {
        this.registeredUsers = userList;
        
        setTitle("QuickChat - Register");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(WHATSAPP_BACKGROUND);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(WHATSAPP_GREEN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        JLabel titleLabel = new JLabel("Create a New Account");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHATSAPP_BACKGROUND);
        formPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add fields
        firstNameField = addFormField(formPanel, "First Name:", 0);
        lastNameField = addFormField(formPanel, "Last Name:", 1);
        usernameField = addFormField(formPanel, "Username (e.g., user_1):", 2);
        cellphoneField = addFormField(formPanel, "Cellphone (+27...):", 3);
        passwordField = new JPasswordField();
        addFormField(formPanel, "Password:", passwordField, 4);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(WHATSAPP_BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        registerButton = new JButton("Register");
        registerButton.setFont(BUTTON_FONT);
        registerButton.setBackground(WHATSAPP_GREEN_ACCENT);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JTextField addFormField(JPanel panel, String labelText, int yPos) {
        JTextField field = new JTextField(20);
        addFormField(panel, labelText, field, yPos);
        return field;
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, int yPos) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(label, gbc);

        field.setFont(INPUT_FONT);
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String fName = firstNameField.getText();
            String lName = lastNameField.getText();
            String uName = usernameField.getText();
            String pWord = new String(passwordField.getPassword());
            String cell = cellphoneField.getText();

            Registration newUser = new Registration();
            ArrayList<String> results = newUser.registerUser(fName, lName, uName, pWord, cell);

            // Display all validation messages
            StringBuilder feedback = new StringBuilder();
            boolean success = true;
            for (String msg : results) {
                feedback.append(msg).append("\n");
                if (msg.contains("failed") || msg.contains("Invalid") || msg.contains("not correctly")) {
                    success = false;
                }
            }

            if (success) {
                registeredUsers.add(newUser);
                JOptionPane.showMessageDialog(this, feedback.toString(), "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Close registration window
                LoginFrontEnd loginForm = new LoginFrontEnd(registeredUsers);
                loginForm.setVisible(true); // Open login window
            } else {
                JOptionPane.showMessageDialog(this, feedback.toString(), "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}