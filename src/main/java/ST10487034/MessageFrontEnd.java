package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MessageFrontEnd extends JFrame implements ActionListener {

    // --- UI Components ---
    private JTextField recipientField;
    private JTextArea payloadArea;
    private JButton sendButton;
    private JButton backButton; // NEW: Back button

    // --- Data ---
    private Registration currentUser;
    private ArrayList<Message> allMessages;
    private int messageLimit; // NEW: To enforce the limit

    public MessageFrontEnd(Registration user, ArrayList<Message> messageList, int limit) {
        this.currentUser = user;
        this.allMessages = messageList;
        this.messageLimit = limit;
        
        setTitle("QuickChat - Compose Message");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(236, 229, 221));
        setLayout(new BorderLayout(15, 15));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(7, 94, 84));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        int remaining = messageLimit - Message.returnTotalMessages();
        JLabel titleLabel = new JLabel("Compose New Message (" + remaining + " remaining)");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(236, 229, 221));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Recipient
        JLabel recipientLabel = new JLabel("Recipient's Number (+27...):");
        recipientLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(recipientLabel, gbc);

        recipientField = new JTextField(30);
        recipientField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(recipientField, gbc);

        // Message Payload
        JLabel payloadLabel = new JLabel("Your Message (max 250 chars):");
        payloadLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(payloadLabel, gbc);

        payloadArea = new JTextArea(10, 30);
        payloadArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
        payloadArea.setLineWrap(true);
        payloadArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(payloadArea);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        formPanel.add(scrollPane, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(236, 229, 221));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        
        sendButton = new JButton("Send Message");
        sendButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        sendButton.setBackground(new Color(37, 211, 102));
        sendButton.setForeground(Color.WHITE);
        sendButton.addActionListener(this);
        buttonPanel.add(sendButton);

        // ** NEW: Add Back Button **
        backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // ** NEW: Disable send button if limit is already reached when window opens **
        if (Message.returnTotalMessages() >= messageLimit) {
            sendButton.setEnabled(false);
            sendButton.setText("Message Limit Reached");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            // Final check
            if (Message.returnTotalMessages() >= messageLimit) {
                JOptionPane.showMessageDialog(this, "Message limit reached.", "Error", JOptionPane.ERROR_MESSAGE);
                sendButton.setEnabled(false);
                return;
            }
            
            String recipient = recipientField.getText();
            String payload = payloadArea.getText();
            
            Message tempMsg = new Message("", "", "", 0);

            String recipientStatus = tempMsg.validateRecipientNumber(recipient);
            if (!recipientStatus.contains("successfully")) {
                JOptionPane.showMessageDialog(this, recipientStatus, "Invalid Recipient", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String payloadStatus = tempMsg.validatePayloadLength(payload);
            if (!payloadStatus.contains("ready")) {
                JOptionPane.showMessageDialog(this, payloadStatus, "Invalid Message Length", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Message newMessage = new Message(currentUser.getCellphone(), recipient, payload, Message.returnTotalMessages());
            newMessage.sendMessage();
            allMessages.add(newMessage);
            
            JOptionPane.showMessageDialog(this, "Message sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else if (e.getSource() == backButton) { // ** NEW: Handle back button click **
            this.dispose(); // Close the compose window
        }
    }
}