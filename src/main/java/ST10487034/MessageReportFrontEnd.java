package ST10487034;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MessageReportFrontEnd extends JFrame implements ActionListener {

    // --- UI Components ---
    private JButton displayAllButton;
    private JButton longestMessageButton;
    private JButton searchButton;
    private JButton backButton; // NEW
    private JTextField searchField;

    // --- Data ---
    private ArrayList<Message> allMessages;
    private Reports reportLogic = new Reports();

    public MessageReportFrontEnd(ArrayList<Message> messageList) {
        this.allMessages = messageList;

        setTitle("QuickChat - Message Reports");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(236, 229, 221));
        setLayout(new BorderLayout(15, 15));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(7, 94, 84));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel titleLabel = new JLabel("Message Reports");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Control Panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(new Color(236, 229, 221));
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons
        displayAllButton = new JButton("Display All Messages");
        displayAllButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        controlPanel.add(displayAllButton, gbc);

        longestMessageButton = new JButton("Find Longest Message");
        longestMessageButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridy = 1;
        controlPanel.add(longestMessageButton, gbc);

        // ** UPDATED: Search Area for Hash **
        gbc.gridwidth = 1; gbc.gridy = 2;
        searchField = new JTextField(20);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        controlPanel.add(searchField, gbc);

        gbc.gridx = 1;
        searchButton = new JButton("Search by Message Hash");
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        controlPanel.add(searchButton, gbc);
        
        // ** NEW: Back button **
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        backButton = new JButton("Back to Dashboard");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        controlPanel.add(backButton, gbc);
        
        // Add listeners
        displayAllButton.addActionListener(this);
        longestMessageButton.addActionListener(this);
        searchButton.addActionListener(this);
        backButton.addActionListener(this);

        add(controlPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayAllButton) {
            String report = reportLogic.generateAllMessagesReport(allMessages);
            JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(report, 20, 50)), "All Messages Report", JOptionPane.PLAIN_MESSAGE);
        
        } else if (e.getSource() == longestMessageButton) {
            String longestMessage = reportLogic.findLongestMessage(allMessages);
            JOptionPane.showMessageDialog(this, "Longest Message:\n\n\"" + longestMessage + "\"", "Longest Message", JOptionPane.INFORMATION_MESSAGE);
        
        } else if (e.getSource() == searchButton) { // ** UPDATED: Search logic **
            String hash = searchField.getText();
            String result = reportLogic.searchMessageByHash(allMessages, hash);
            JOptionPane.showMessageDialog(this, result, "Search Result", JOptionPane.PLAIN_MESSAGE);
        
        } else if (e.getSource() == backButton) { // ** NEW: Handle back button **
            this.dispose();
        }
    }
}