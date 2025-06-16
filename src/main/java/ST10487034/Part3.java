package ST10487034;

import java.util.ArrayList;

// This class implements the reporting and message deletion functionalities
// for the QuickChat application, using data from Part2.
public class Part3 {

    // Reference to the Part2 instance to access the list of messages.
    private Part2 part2MessageManager;

    public Part3(Part2 part2MessageManager) {
        this.part2MessageManager = part2MessageManager;
    }

    // Displays all messages (both SENT and DRAFT) in a formatted string.
    public String displayAllMessages() {
        ArrayList<Part2.Message> allMessages = part2MessageManager.getMessages();
        if (allMessages.isEmpty()) {
            return "No messages available to display.";
        }
        StringBuilder report = new StringBuilder("--- All Messages ---\n");
        int index = 0;
        for (Part2.Message msg : allMessages) {
            report.append("Message ").append(index++).append(":\n");
            report.append("  From: ").append(msg.getSenderUsername()).append("\n");
            report.append("  To: ").append(msg.getReceiverUsername()).append("\n");
            report.append("  Type: ").append(msg.getMessageType()).append("\n");
            report.append("  Content: ").append(msg.getContent()).append("\n\n");
        }
        return report.toString();
    }

    // Searches for messages sent by a specific user and returns them as a formatted string.
    public String searchMessagesBySender(String senderUsername) {
        ArrayList<Part2.Message> allMessages = part2MessageManager.getMessages();
        StringBuilder report = new StringBuilder("--- Messages by Sender: " + senderUsername + " ---\n");
        boolean found = false;
        for (Part2.Message msg : allMessages) {
            if (msg.getSenderUsername().equalsIgnoreCase(senderUsername)) {
                report.append("  To: ").append(msg.getReceiverUsername()).append("\n");
                report.append("  Type: ").append(msg.getMessageType()).append("\n");
                report.append("  Content: ").append(msg.getContent()).append("\n\n");
                found = true;
            }
        }
        if (!found) {
            report.append("No messages found for sender: ").append(senderUsername).append(".\n");
        }
        return report.toString();
    }

    // Displays only drafted messages in a formatted string.
    public String displayDraftedMessages() {
        ArrayList<Part2.Message> allMessages = part2MessageManager.getMessages();
        StringBuilder report = new StringBuilder("--- Drafted Messages ---\n");
        boolean found = false;
        for (Part2.Message msg : allMessages) {
            if (msg.getMessageType() == Part2.MessageType.DRAFT) {
                report.append("  From: ").append(msg.getSenderUsername()).append("\n");
                report.append("  To: ").append(msg.getReceiverUsername()).append("\n");
                report.append("  Content: ").append(msg.getContent()).append("\n\n");
                found = true;
            }
        }
        if (!found) {
            report.append("No drafted messages available.\n");
        }
        return report.toString();
    }

    // Deletes a message based on its index in the messages list from Part2.
    // Returns true if deletion is successful, false otherwise.
    public boolean deleteMessage(int index) {
        ArrayList<Part2.Message> messages = part2MessageManager.getMessages();
        if (index >= 0 && index < messages.size()) {
            messages.remove(index);
            // After modifying the list, Part2 needs to save it again.
            // This implicitly relies on Part2's save method, typically triggered by an action like send/draft.
          
            try {
            
                java.lang.reflect.Method saveMethod = Part2.class.getDeclaredMethod("saveMessagesToJson");
                saveMethod.setAccessible(true); // Allow access to private method
                saveMethod.invoke(part2MessageManager);
            } catch (Exception e) {
                System.err.println("Error forcing save after delete: " + e.getMessage());
            }
// NOTE !, This console logs are simply for the unit tests I wrote, not to be marked as part of the UI
            System.out.println("Message at index " + index + " deleted successfully.");
            return true;
        }
        System.out.println("Deletion failed: Invalid message index " + index);
        return false;
    }
}
