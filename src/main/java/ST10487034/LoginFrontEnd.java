package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginFrontEnd extends JFrame implements ActionListener {

    // --- UI Styling ---
    private static final Color WHATSAPP_GREEN_PRIMARY = new Color(7, 94, 84);
    private static final Color WHATSAPP_GREEN_ACCENT = new Color(37, 211, 102);
    private static final Color WHATSAPP_BACKGROUND = new Color(236, 229, 221);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

    // --- UI Components ---
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    // --- Data Management ---
    private ArrayList<Registration> registeredUsers;

    public LoginFrontEnd(ArrayList<Registration> userList) {
        this.registeredUsers = userList;

        setTitle("QuickChat - Login");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(WHATSAPP_BACKGROUND);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(WHATSAPP_GREEN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        JLabel titleLabel = new JLabel("Welcome Back! Please Login");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHATSAPP_BACKGROUND);
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        
        usernameField = addFormField(formPanel, "Username:", 0);
        passwordField = new JPasswordField(20);
        addFormField(formPanel, "Password:", passwordField, 1);
        
        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(WHATSAPP_BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        loginButton = new JButton("Login");
        loginButton.setFont(BUTTON_FONT);
        loginButton.setBackground(WHATSAPP_GREEN_ACCENT);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JTextField addFormField(JPanel panel, String labelText, int yPos) {
        JTextField field = new JTextField(20);
        addFormField(panel, labelText, field, yPos);
        return field;
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, int yPos) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
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
        if (e.getSource() == loginButton) {
            String uName = usernameField.getText();
            String pWord = new String(passwordField.getPassword());
            
            Login loginLogic = new Login();
            Registration foundUser = null;
            
            // Find the user in our list
            for(Registration user : registeredUsers) {
                if(user.getUsername().equals(uName)) {
                    foundUser = user;
                    break;
                }
            }

            if (foundUser != null && loginLogic.loginUser(uName, pWord, foundUser)) {
                String welcomeMsg = loginLogic.returnLoginStatus(foundUser);
                JOptionPane.showMessageDialog(this, welcomeMsg, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                // Open the main dashboard
                PrincessPOE dashboard = new PrincessPOE(foundUser);
                dashboard.setVisible(true);
            } else {
                // To avoid giving away which field is wrong, we use a generic error user
                // and call the methods to get the generic failure message.
                loginLogic.loginUser("", "", new Registration()); 
                String errorMsg = loginLogic.returnLoginStatus(null);
                JOptionPane.showMessageDialog(this, errorMsg, "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}