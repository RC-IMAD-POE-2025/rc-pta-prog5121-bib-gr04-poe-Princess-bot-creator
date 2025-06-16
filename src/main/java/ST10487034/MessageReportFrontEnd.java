package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This class provides the graphical user interface for viewing and managing message reports.
// It displays all messages, allows searching by sender, showing drafts, and deleting messages.
public class MessageReportFrontEnd extends JFrame implements ActionListener {

    // Color Palette
    private static final Color WHATSAPP_GREEN_PRIMARY = new Color(7, 94, 84); // Dark header/accent green
    private static final Color WHATSAPP_GREEN_ACCENT = new Color(37, 211, 102); // Lighter button/status green
    private static final Color WHATSAPP_BACKGROUND = new Color(236, 229, 221); // Light chat background
    private static final Color MODERN_TEXT_DARK = new Color(33, 33, 33);
    private static final Color MODERN_TEXT_LIGHT = new Color(97, 97, 97);
    private static final Color FIELD_BACKGROUND = Color.WHITE; // Clean white for inputs
    private static final Color ERROR_RED = new Color(220, 50, 50);

    // Fonts
    private static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 15);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

    // GUI Components
    private JLabel reportsTitleLabel;
    private JTextArea reportsDisplayArea;
    private JButton displayAllButton;
    private JTextField searchSenderField;
    private JButton searchButton;
    private JButton displayDraftsButton;
    private JTextField deleteIndexField;
    private JButton deleteButton;
    private JButton closeButton; // Button to close this report window

    // Logic manager
    private Part3 reportManager;
    private Part2 messageManager; // Needed for message operations that trigger save

    // Constructor takes a MessageManager instance to access message data.
    public MessageReportFrontEnd(Part2 messageManager) {
        this.messageManager = messageManager; // Keep reference to message manager
        this.reportManager = new Part3(messageManager); // Part3 needs message manager instance

        // Frame Setup
        setTitle("QuickChatApp - Message Reports");
        setSize(1280, 720); // Adjusted size for reports
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Only close this window
        setLocationRelativeTo(null); // Center on screen

        // Set overall background
        getContentPane().setBackground(WHATSAPP_BACKGROUND);
        setLayout(new BorderLayout(15, 15)); // Padding around main content

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        headerPanel.setBackground(WHATSAPP_GREEN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(15, 0, 15, 0)); // Padding inside header
        reportsTitleLabel = new JLabel("QuickChatApp - Message Reports");
        reportsTitleLabel.setFont(HEADER_FONT);
        reportsTitleLabel.setForeground(Color.WHITE);
        headerPanel.add(reportsTitleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Report Display Area
        reportsDisplayArea = new JTextArea();
        reportsDisplayArea.setEditable(false);
        reportsDisplayArea.setFont(INPUT_FONT);
        reportsDisplayArea.setLineWrap(true);
        reportsDisplayArea.setWrapStyleWord(true);
        reportsDisplayArea.setBackground(FIELD_BACKGROUND);
        reportsDisplayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Inner padding
        JScrollPane scrollPane = new JScrollPane(reportsDisplayArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker(), 1), // Border for the whole display area
            new EmptyBorder(5, 5, 5, 5) // Padding
        ));
        add(scrollPane, BorderLayout.CENTER);

        // Control Panel (Buttons and Input)
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(WHATSAPP_BACKGROUND);
        controlPanel.setLayout(new GridBagLayout()); // Flexible layout for controls
        controlPanel.setBorder(new EmptyBorder(10, 15, 10, 15)); // Padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

        // Row 1: Display All Messages Button
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; // Span two columns
        displayAllButton = new JButton("Display All Messages");
        displayAllButton.setFont(BUTTON_FONT);
        displayAllButton.setBackground(WHATSAPP_GREEN_ACCENT);
        displayAllButton.setForeground(Color.WHITE);
        displayAllButton.setFocusPainted(false);
        displayAllButton.addActionListener(this);
        controlPanel.add(displayAllButton, gbc);

        // Row 2: Search by Sender
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; // Reset gridwidth
        gbc.anchor = GridBagConstraints.EAST;
        JLabel searchLabel = new JLabel("Search by Sender Username:");
        searchLabel.setFont(LABEL_FONT);
        searchLabel.setForeground(MODERN_TEXT_DARK);
        controlPanel.add(searchLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        searchSenderField = new JTextField(15);
        searchSenderField.setFont(INPUT_FONT);
        searchSenderField.setBackground(FIELD_BACKGROUND);
        searchSenderField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        controlPanel.add(searchSenderField, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        searchButton = new JButton("Search"); // Shorter text
        searchButton.setFont(BUTTON_FONT.deriveFont(Font.PLAIN, 15f));
        searchButton.setBackground(WHATSAPP_GREEN_PRIMARY.brighter());
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(this);
        controlPanel.add(searchButton, gbc);

        // Row 3: Display Drafted Messages Button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3; // Span all columns
        displayDraftsButton = new JButton("Display Drafted Messages");
        displayDraftsButton.setFont(BUTTON_FONT);
        displayDraftsButton.setBackground(WHATSAPP_GREEN_ACCENT);
        displayDraftsButton.setForeground(Color.WHITE);
        displayDraftsButton.setFocusPainted(false);
        displayDraftsButton.addActionListener(this);
        controlPanel.add(displayDraftsButton, gbc);

        // Row 4: Delete Message by Index
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        JLabel deleteLabel = new JLabel("Delete Message (Index):");
        deleteLabel.setFont(LABEL_FONT);
        deleteLabel.setForeground(MODERN_TEXT_DARK);
        controlPanel.add(deleteLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        deleteIndexField = new JTextField(5);
        deleteIndexField.setFont(INPUT_FONT);
        deleteIndexField.setBackground(FIELD_BACKGROUND);
        deleteIndexField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHATSAPP_GREEN_ACCENT.darker().darker(), 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        controlPanel.add(deleteIndexField, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0;
        deleteButton = new JButton("Delete"); // Shorter text
        deleteButton.setFont(BUTTON_FONT.deriveFont(Font.PLAIN, 15f));
        deleteButton.setBackground(ERROR_RED); // Red for delete action
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(this);
        controlPanel.add(deleteButton, gbc);

        // Row 5: Close Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        closeButton = new JButton("Close Reports");
        closeButton.setFont(BUTTON_FONT);
        closeButton.setBackground(WHATSAPP_GREEN_PRIMARY.darker()); // Even darker for closing
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(this);
        controlPanel.add(closeButton, gbc);

        add(controlPanel, BorderLayout.SOUTH);

        // Initial display of all messages when window opens
        reportsDisplayArea.setText(reportManager.displayAllMessages());
    }

    // Handles button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayAllButton) {
            String report = reportManager.displayAllMessages();
            reportsDisplayArea.setText(report);
        } else if (e.getSource() == searchButton) {
            String sender = searchSenderField.getText().trim();
            if (sender.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a sender username to search.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String report = reportManager.searchMessagesBySender(sender);
            reportsDisplayArea.setText(report);
        } else if (e.getSource() == displayDraftsButton) {
            String report = reportManager.displayDraftedMessages();
            reportsDisplayArea.setText(report);
        } else if (e.getSource() == deleteButton) {
            try {
                int indexToDelete = Integer.parseInt(deleteIndexField.getText().trim());
                if (reportManager.deleteMessage(indexToDelete)) {
                    JOptionPane.showMessageDialog(this, "Message deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    reportsDisplayArea.setText(reportManager.displayAllMessages()); // Refresh report
                    deleteIndexField.setText(""); // Clear field
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid index or message not found.", "Deletion Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for the index.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == closeButton) {
            this.dispose(); // Close the reports window
        }
    }
}
