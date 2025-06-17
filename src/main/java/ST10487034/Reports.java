package ST10487034;

import java.util.ArrayList;

public class Reports {

    /**
     * Generates a string report of all messages.
     * @param allMessages The list of all messages.
     * @return A formatted string report.
     */
    public String generateAllMessagesReport(ArrayList<Message> allMessages) {
        if (allMessages == null || allMessages.isEmpty()) {
            return "No messages to display.";
        }
        StringBuilder report = new StringBuilder("--- All Messages Report ---\n\n");
        for (Message msg : allMessages) {
            report.append("ID: ").append(msg.getMessageID()).append("\n");
            report.append("Hash: ").append(msg.getMessageHash()).append("\n");
            report.append("To: ").append(msg.getRecipientNumber()).append("\n");
            report.append("Message: ").append(msg.getPayload()).append("\n\n");
        }
        return report.toString();
    }

    /**
     * Finds the message with the longest payload text.
     * @param allMessages The list of all messages.
     * @return The payload of the longest message, or a status message if none exist.
     */
    public String findLongestMessage(ArrayList<Message> allMessages) {
        if (allMessages == null || allMessages.isEmpty()) {
            return "No messages to analyze.";
        }
        Message longest = allMessages.get(0);
        for (Message msg : allMessages) {
            if (msg.getPayload().length() > longest.getPayload().length()) {
                longest = msg;
            }
        }
        return longest.getPayload();
    }
    
    /**
     * ** UPDATED: Searches for a message by its hash code. **
     * @param allMessages The list of messages to search through.
     * @param messageHash The hash to search for.
     * @return A string with the found message details, or a "not found" message.
     */
    public String searchMessageByHash(ArrayList<Message> allMessages, String messageHash) {
        if (messageHash == null || messageHash.trim().isEmpty()) {
            return "Message hash cannot be empty.";
        }
        for (Message msg : allMessages) {
            if (msg.getMessageHash().equalsIgnoreCase(messageHash.trim())) {
                return "--- Message Found! ---\n\n" +
                       "Recipient: " + msg.getRecipientNumber() + "\n" +
                       "Message: " + msg.getPayload();
            }
        }
        return "Message with hash " + messageHash + " not found.";
    }
}