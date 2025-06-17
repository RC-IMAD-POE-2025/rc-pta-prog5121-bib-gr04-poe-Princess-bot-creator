package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PrincessPOE extends JFrame implements ActionListener {

    // --- Data Management ---
    private static ArrayList<Registration> registeredUsers = new ArrayList<>();
    private static ArrayList<Message> allMessages = new ArrayList<>();
    private Registration currentUser;
    private int messageLimit; // NEW: To store the message limit for the session

    // --- UI Styling ---
    private static final Color WHATSAPP_GREEN_PRIMARY = new Color(7, 94, 84);
    private static final Color WHATSAPP_GREEN_ACCENT = new Color(37, 211, 102);
    private static final Color WHATSAPP_BACKGROUND = new Color(236, 229, 221);
    private static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 28);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 18);

    // --- UI Components ---
    private JButton composeButton;
    private JButton reportsButton;
    private JButton logoutButton;

    public static void main(String[] args) {
        RegistrationFrontEnd regForm = new RegistrationFrontEnd(registeredUsers);
        regForm.setVisible(true);
    }
    
    public PrincessPOE(Registration user) {
        this.currentUser = user;
        
        // ** NEW: Ask for message limit upon login **
        askForMessageLimit();
        
        setTitle("QuickChat - Dashboard");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(WHATSAPP_BACKGROUND);
        setLayout(new BorderLayout(20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(WHATSAPP_GREEN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("QuickChat Dashboard");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFirstName() + "! You can send " + messageLimit + " message(s).");
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        welcomeLabel.setForeground(Color.WHITE);
        headerPanel.add(welcomeLabel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(WHATSAPP_BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        composeButton = createDashboardButton("Compose New Message", WHATSAPP_GREEN_ACCENT);
        reportsButton = createDashboardButton("View Reports", WHATSAPP_GREEN_ACCENT);
        logoutButton = createDashboardButton("Logout", new Color(220, 70, 70));
        
        gbc.gridy = 0;
        buttonPanel.add(composeButton, gbc);
        gbc.gridy = 1;
        buttonPanel.add(reportsButton, gbc);
        gbc.gridy = 2;
        buttonPanel.add(logoutButton, gbc);
        
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    // ** NEW: Method to get message limit from user **
    private void askForMessageLimit() {
        boolean valid_input = false;
        while (!valid_input) {
            try {
                String input = JOptionPane.showInputDialog(this, "How many messages would you like to send?", "Set Message Limit", JOptionPane.QUESTION_MESSAGE);
                if (input == null) { // User closed the dialog
                    this.messageLimit = 0;
                    break;
                }
                this.messageLimit = Integer.parseInt(input);
                if (this.messageLimit < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a non-negative number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    continue; // Ask again
                }
                valid_input = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private JButton createDashboardButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(20, 40, 20, 40));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == composeButton) {
            if (Message.returnTotalMessages() >= messageLimit) {
                JOptionPane.showMessageDialog(this, "You have reached your message limit of " + messageLimit + ".", "Limit Reached", JOptionPane.WARNING_MESSAGE);
                return;
            }
            MessageFrontEnd msgForm = new MessageFrontEnd(currentUser, allMessages, messageLimit);
            msgForm.setVisible(true);
        } else if (e.getSource() == reportsButton) {
            MessageReportFrontEnd reportForm = new MessageReportFrontEnd(allMessages);
            reportForm.setVisible(true);
        } else if (e.getSource() == logoutButton) {
            JOptionPane.showMessageDialog(this, "You have been logged out.");
            this.dispose();
            LoginFrontEnd loginForm = new LoginFrontEnd(registeredUsers);
            loginForm.setVisible(true);
        }
    }
}