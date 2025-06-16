package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.TitledBorder;

// This class provides the main chat messaging graphical user interface.
// A logged-in user can send messages, save drafts, and navigate to reports.
public class MessageFrontEnd extends JFrame implements ActionListener {

    // Color Palette
    private static final Color WHATSAPP_GREEN_PRIMARY = new Color(7, 94, 84); // Dark header/accent green
    private static final Color WHATSAPP_GREEN_ACCENT = new Color(37, 211, 102); // Lighter button/status green
    private static final Color WHATSAPP_BACKGROUND = new Color(236, 229, 221); // Light chat background
    private static final Color MODERN_TEXT_DARK = new Color(33, 33, 33);
    private static final Color MODERN_TEXT_LIGHT = new Color(97, 97, 97);
    private static final Color FIELD_BACKGROUND = Color.WHITE; // Clean white for inputs
    private static final Color MESSAGE_BUBBLE_SENT = new Color(220, 248, 198); // Light green bubble
    private static final Color MESSAGE_BUBBLE_RECEIVED = Color.WHITE; // White bubble

    // Fonts
    private static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Font STATUS_FONT = new Font("SansSerif", Font.ITALIC, 14);

    // GUI Components
    private JLabel chatTitleLabel;
    private JLabel loggedInUserLabel;
    private JTextField receiverUsernameField;
    private JTextArea messageContentArea;
    private JButton sendMessageButton;
    private JButton saveDraftButton;
    private JButton viewReportsButton;
    private JButton logoutButton;

    // Logic managers
    private Part1.User currentUser;
    private Part2 messageManager;

    // Constructor requires the logged-in User object.
    public MessageFrontEnd(Part1.User user) {
        this.currentUser = user; // Store the logged-in user
        messageManager = new Part2(); // Initialize message manager

        // Frame Setup
        setTitle("QuickChatApp - Messaging");
        setSize(1280, 720); // Adjusted size for chat UI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app on close
        setLocationRelativeTo(null); // Center on screen

        // Set overall background
        getContentPane().setBackground(WHATSAPP_BACKGROUND);
        setLayout(new BorderLayout(15, 15)); // Padding around main content

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(WHATSAPP_GREEN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20)); // Padding inside header

        chatTitleLabel = new JLabel("QuickChatApp", SwingConstants.CENTER);
        chatTitleLabel.setFont(HEADER_FONT);
        chatTitleLabel.setForeground(Color.WHITE);
        headerPanel.add(chatTitleLabel, BorderLayout.CENTER);

        loggedInUserLabel = new JLabel("Logged in as: " + currentUser.getFirstName() + " (" + currentUser.getUsername() + ")");
        loggedInUserLabel.setFont(LABEL_FONT);
        loggedInUserLabel.setForeground(Color.WHITE.brighter());
        headerPanel.add(loggedInUserLabel, BorderLayout.EAST); // Place user info on the right

        add(headerPanel, BorderLayout.NORTH);

        // Chat Area (Placeholder for actual chat history, not implemented for this POE part)
        // For a real WhatsApp clone, this would be a JList or JTable displaying messages.
        // For POE, we focus on input/output logic and reports, so this is a simple placeholder.
        JPanel chatDisplayArea = new JPanel();
        chatDisplayArea.setBackground(WHATSAPP_BACKGROUND);
        chatDisplayArea.setLayout(new BorderLayout());
        chatDisplayArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(WHATSAPP_GREEN_PRIMARY.brighter(), 1),
                "Chat History (Placeholder)",
                TitledBorder.LEFT, TitledBorder.TOP, LABEL_FONT, MODERN_TEXT_LIGHT
            ),
            new EmptyBorder(10, 10, 10, 10)
        ));
        JLabel placeholderText = new JLabel("<html><i>Your chat messages would appear here.<br>For now, focus on sending messages and checking reports!</i></html>", SwingConstants.CENTER);
        placeholderText.setFont(STATUS_FONT);
        placeholderText.setForeground(MODERN_TEXT_LIGHT);
        chatDisplayArea.add(placeholderText, BorderLayout.CENTER);
        add(chatDisplayArea, BorderLayout.CENTER);


        // Message Input Panel
        JPanel messageInputPanel = new JPanel(new BorderLayout(10, 10)); // Spacing
        messageInputPanel.setBackground(WHATSAPP_BACKGROUND);
        messageInputPanel.setBorder(new EmptyBorder(0, 15, 0, 15)); // Horizontal padding

        JPanel receiverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        receiverPanel.setBackground(WHATSAPP_BACKGROUND);
        JLabel receiverLabel = new JLabel("Send to Username:");
        receiverLabel.setFont(LABEL_FONT);
        receiverLabel.setForeground(MODERN_TEXT_DARK);
        receiverPanel.add(receiverLabel);
        receiverUsernameField = new JTextField(20);
        receiverUsernameField.setFont(INPUT_FONT);
        receiverUsernameField.setBackground(FIELD_BACKGROUND);
        receiverUsernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        receiverPanel.add(receiverUsernameField);
        messageInputPanel.add(receiverPanel, BorderLayout.NORTH);

        messageContentArea = new JTextArea(5, 40); // Rows, columns
        messageContentArea.setFont(INPUT_FONT);
        messageContentArea.setLineWrap(true);
        messageContentArea.setWrapStyleWord(true);
        messageContentArea.setBackground(FIELD_BACKGROUND);
        messageContentArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollPane = new JScrollPane(messageContentArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove default scroll pane border
        messageInputPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Spacing between buttons
        buttonPanel.setBackground(WHATSAPP_BACKGROUND);

        sendMessageButton = new JButton("Send Message");
        sendMessageButton.setFont(BUTTON_FONT);
        sendMessageButton.setBackground(WHATSAPP_GREEN_ACCENT);
        sendMessageButton.setForeground(Color.WHITE);
        sendMessageButton.setFocusPainted(false);
        sendMessageButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker(), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        sendMessageButton.addActionListener(this);
        buttonPanel.add(sendMessageButton);

        saveDraftButton = new JButton("Save Draft");
        saveDraftButton.setFont(BUTTON_FONT);
        saveDraftButton.setBackground(WHATSAPP_GREEN_ACCENT);
        saveDraftButton.setForeground(Color.WHITE);
        saveDraftButton.setFocusPainted(false);
        saveDraftButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker(), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        saveDraftButton.addActionListener(this);
        buttonPanel.add(saveDraftButton);

        viewReportsButton = new JButton("View Reports");
        viewReportsButton.setFont(BUTTON_FONT);
        viewReportsButton.setBackground(WHATSAPP_GREEN_PRIMARY); // Different color for navigation
        viewReportsButton.setForeground(Color.WHITE);
        viewReportsButton.setFocusPainted(false);
        viewReportsButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_PRIMARY.darker(), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        viewReportsButton.addActionListener(this);
        buttonPanel.add(viewReportsButton);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(BUTTON_FONT);
        logoutButton.setBackground(new Color(220, 70, 70)); // Red for logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 50, 50), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        logoutButton.addActionListener(this);
        buttonPanel.add(logoutButton);


        messageInputPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(messageInputPanel, BorderLayout.SOUTH);
    }

    // Handles button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendMessageButton) {
            String receiver = receiverUsernameField.getText().trim();
            String content = messageContentArea.getText().trim();

            if (receiver.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter recipient and message content.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            messageManager.sendMessage(currentUser.getUsername(), receiver, content);
            JOptionPane.showMessageDialog(this, "Message sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            receiverUsernameField.setText("");
            messageContentArea.setText("");
        } else if (e.getSource() == saveDraftButton) {
            String receiver = receiverUsernameField.getText().trim(); // Receiver can be empty for drafts
            String content = messageContentArea.getText().trim();

            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Draft content cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            messageManager.draftMessage(currentUser.getUsername(), receiver, content); // Receiver can be empty string
            JOptionPane.showMessageDialog(this, "Draft saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            messageContentArea.setText("");
        } else if (e.getSource() == viewReportsButton) {
            MessageReportFrontEnd reportsForm = new MessageReportFrontEnd(messageManager);
            reportsForm.setVisible(true);
            // This window stays open, reports opens as a separate dialog.
        } else if (e.getSource() == logoutButton) {
            // Close chat window and open new login window
            LoginFrontEnd loginForm = new LoginFrontEnd();
            loginForm.setVisible(true);
            this.dispose(); // Close current chat window
        }
    }
}
