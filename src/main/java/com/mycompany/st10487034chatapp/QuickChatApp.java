package com.mycompany.st10487034chatapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Princess
 */
public class QuickChatApp {

    public static void RunQuickChatApp() throws IOException {

        /*    boolean loggedIn = login();

        if (!(loggedIn)) {
            JOptionPane.showMessageDialog(null, "Login Failed, Existing");
            return;
        }*/
        JOptionPane.showMessageDialog(null, "Welcome to Quick Chat");

        int sentCount = 0;
        int messageLimit = -1;
        while (true) {
            String input = JOptionPane.showInputDialog("Select an option:\n1)Send Messages\n2)Show recent Messages \n3)Display report \n4)Display sent message sender/recipient \n5)Delete Message \n6)Search Recipient Messages \n7)Message ID Search \n8)Display Longest Message \n10)Quit");
            if (input == null) {
                break;
            }
            int option = Integer.parseInt(input);
            switch (option) {
                case 1:

                    if (messageLimit < 1) {

                        messageLimit = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to enter?"));
                    }
                    if (messageLimit == sentCount) {
                        JOptionPane.showMessageDialog(null, "Message limit reached");

                    }
                    for (int m = sentCount; m < messageLimit; m++) {
                        Message newMessage = new Message((sentCount + 1));
                        String recipient = JOptionPane.showInputDialog("Enter recipient  " + (sentCount + 1) + " cell number (+country code..))");
                        while (!newMessage.setRecipientCell(recipient)) {
                            recipient = JOptionPane.showInputDialog("Invalid Cell. Try again. Enter recipient  " + (sentCount + 1) + " cell number (+country code..))");
                        }
                        String message = JOptionPane.showInputDialog("Enter a message " + (sentCount + 1) + " (max 250 characters)");
                        while (!newMessage.setMessage(message)) {
                            message = JOptionPane.showInputDialog("Invalid Message. Try again. Enter a message  " + (sentCount + 1) + " (max 250 characters)");
                        }

                        String[] options = {"Send message", "Discard message", "Store message"};
                        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Send message",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                        switch (choice) {

                            case 0:
                                ST10487034ChatApp.SentMessages.add(newMessage);
                                ST10487034ChatApp.MessageHashes.add(newMessage.createMessageHash());
                                JOptionPane.showMessageDialog(null, "Message sent\n" + newMessage.printMessage());
                                sentCount++;
                                break;
                            case 1:
                                ST10487034ChatApp.DicardedMessages.add(newMessage);
                                ST10487034ChatApp.MessageHashes.add(newMessage.createMessageHash());
                                JOptionPane.showMessageDialog(null, "Message discarded\n" + newMessage.printMessage());
                                sentCount++;
                                break;
                            case 2:
                                ST10487034ChatApp.StoredMessages.add(newMessage);
                                ST10487034ChatApp.MessageHashes.add(newMessage.createMessageHash());
                                newMessage.storeMessages(ST10487034ChatApp.StoredMessages);
                                //storedMessages.add(newMessage);
                                //newMessage.storeMessages(storedMessages);
                                JOptionPane.showMessageDialog(null, "Message stored\n" + newMessage.printMessage());
                                sentCount++;
                                break;
                        }
                    }
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Coming Soon");
                    break;
                case 3:
                    String formattedReport = "";
                    for (Message sentMessage : ST10487034ChatApp.SentMessages) {

                        formattedReport += sentMessage.printMessage() + "\n\n---------------------------------------\n\n";
                    }
                    JOptionPane.showMessageDialog(null, "-------Sent Messages-----\n" + formattedReport);
                    break;
                case 4:
                    String formattedMessages = "";
                    for (Message sentMessage : ST10487034ChatApp.SentMessages) {

                        formattedMessages += "MessageId: " + sentMessage.messageID + " Sender: " + ST10487034ChatApp.LoginUser.getLoggedinUser().registeredUsername + " Recipient: " + sentMessage.recipientCell + "\n\n--------------------------\n\n";
                    }
                    JOptionPane.showMessageDialog(null, "-------Display sent message sender/recipient-----\n" + formattedMessages);
                    break;
                case 5:
                    String messageHash = JOptionPane.showInputDialog("Enter Message Hash to be deleted");

                    if (deleteMessage(messageHash, ST10487034ChatApp.SentMessages)
                            || deleteMessage(messageHash, ST10487034ChatApp.StoredMessages)
                            || deleteMessage(messageHash, ST10487034ChatApp.DicardedMessages)) {
                        break;
                    }

                    JOptionPane.showMessageDialog(null, "Message Could Not be found.");
                    break;
                case 6:
                    String cellphoneNumber = JOptionPane.showInputDialog("Enter Recipient Cellphone Number to search");
                    String recipientMessages = getRecipientMessages(cellphoneNumber);
                    JOptionPane.showMessageDialog(null, recipientMessages);
                    break;
                case 7:
                    String searchMessageId = JOptionPane.showInputDialog("Enter Message ID");
                    boolean messageFound = false;
                    for (Message searchMessage : ST10487034ChatApp.SentMessages) {
                        if (searchMessage.messageID.equals(searchMessageId)) {
                            JOptionPane.showMessageDialog(null, "Recipient: " + searchMessage.recipientCell
                                    + " Message: " + searchMessage.messageText);
                            messageFound = true;
                            break;
                        }
                    }
                    if (!messageFound) {
                        JOptionPane.showMessageDialog(null, "Message ID: " + searchMessageId + " Not Found");
                    }
                    break;
                case 8:
                    JOptionPane.showMessageDialog(null, "Longest message:" + getLongestMessage());
                    break;
                case 10:
                    JOptionPane.showMessageDialog(null, "Total messages sent:" + sentCount + "\nExiting QuickChat");
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option, Please choose 1,2,3 etc.");

            }
        }
    }

    private static boolean deleteMessage(String messageHash, ArrayList<Message> messages) {
        for (Message deleteSentMessage : messages) {
            if (deleteSentMessage.createMessageHash().equals(messageHash)) {
                if (messages.remove(deleteSentMessage)) {
                    JOptionPane.showMessageDialog(null, "Sent message deleted successfully.\n " + deleteSentMessage.messageText);
                    return true;
                }
            }
        }
        return false;
    }

    private static String getRecipientMessages(String cellphoneNumber) {
        ArrayList<Message> combined = new ArrayList<>();

        combined.addAll(ST10487034ChatApp.SentMessages);
        combined.addAll(ST10487034ChatApp.StoredMessages);
        String recipientMessages = "";
        for (Message searchMessage : combined) {
            if (searchMessage.recipientCell.equals(cellphoneNumber)) {
                recipientMessages += searchMessage.messageText + ", ";
            }
        }
        
        return recipientMessages;
    }

    private static String getLongestMessage() {
        ArrayList<Message> combined = new ArrayList<>();

        combined.addAll(ST10487034ChatApp.DicardedMessages);
        combined.addAll(ST10487034ChatApp.SentMessages);
        combined.addAll(ST10487034ChatApp.StoredMessages);

        String longestMessage = "";
        for (Message candidate : combined) {
            if (longestMessage.length() < candidate.messageText.length()) {
                longestMessage = candidate.messageText;
            }
        }
        return longestMessage;
    }

}
