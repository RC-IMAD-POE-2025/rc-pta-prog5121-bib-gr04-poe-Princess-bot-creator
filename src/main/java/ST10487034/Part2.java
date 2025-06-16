package ST10487034;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// This class handles the core messaging operations: sending and drafting messages.
// It also manages the persistence of these messages to a JSON file.
public class Part2 {

    // Enum to categorize message types (Sent or Draft).
    public enum MessageType {
        SENT,
        DRAFT
    }

    // Inner class to represent a Message.
    public static class Message {
        private String senderUsername;
        private String receiverUsername;
        private String content;
        private MessageType messageType;

        public Message(String senderUsername, String receiverUsername, String content, MessageType messageType) {
            this.senderUsername = senderUsername;
            this.receiverUsername = receiverUsername;
            this.content = content;
            this.messageType = messageType;
        }

        public String getSenderUsername() { return senderUsername; }
        public String getReceiverUsername() { return receiverUsername; }
        public String getContent() { return content; }
        public MessageType getMessageType() { return messageType; }

        @Override
        public String toString() {
            return "From: " + senderUsername + ", To: " + receiverUsername +
                   ", Type: " + messageType + "\nContent: " + content;
        }
    }

    // List to store all messages (both sent and drafted).
    private ArrayList<Message> messages;
    // File where message data will be stored.
    private static final String MESSAGES_FILE = "messages.json";

    public Part2() {
        messages = new ArrayList<>();
        // Load existing messages when this class is created.
        loadMessagesFromJson();
    }

    // Gets the list of all messages. This is crucial for Part3 to access messages.
    public ArrayList<Message> getMessages() {
        return messages;
    }

    // Sends a new message and adds it to the list as SENT.
    public void sendMessage(String sender, String receiver, String content) {
        Message newMessage = new Message(sender, receiver, content, MessageType.SENT);
        messages.add(newMessage);
        saveMessagesToJson(); // Save changes to file.
        System.out.println("Message sent from " + sender + " to " + receiver);
    }

    // Saves a message as a DRAFT.
    public void draftMessage(String sender, String receiver, String content) {
        Message newDraft = new Message(sender, receiver, content, MessageType.DRAFT);
        messages.add(newDraft);
        saveMessagesToJson(); // Save changes to file.
        System.out.println("Message drafted by " + sender);
    }

    // Saves the current list of messages to a JSON file for persistence.
    @SuppressWarnings("unchecked")
    private void saveMessagesToJson() {
        JSONArray messageList = new JSONArray();
        for (Message msg : messages) {
            JSONObject messageObject = new JSONObject();
            messageObject.put("senderUsername", msg.getSenderUsername());
            messageObject.put("receiverUsername", msg.getReceiverUsername());
            messageObject.put("content", msg.getContent());
            messageObject.put("messageType", msg.getMessageType().name()); // Store enum as string.
            messageList.add(messageObject);
        }

        try (FileWriter file = new FileWriter(MESSAGES_FILE)) {
            file.write(messageList.toJSONString());
            file.flush();
            System.out.println("Message data saved to " + MESSAGES_FILE);
        } catch (IOException e) {
            System.err.println("Error saving messages to JSON: " + e.getMessage());
        }
    }

    // Loads message data from the JSON file when the application starts.
    private void loadMessagesFromJson() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(MESSAGES_FILE)) {
            Object obj = parser.parse(reader);
            JSONArray messageList = (JSONArray) obj;
            messages.clear(); // Clear existing list before loading from file.

            for (Object msgObj : messageList) {
                JSONObject messageJson = (JSONObject) msgObj;
                String senderUsername = (String) messageJson.get("senderUsername");
                String receiverUsername = (String) messageJson.get("receiverUsername");
                String content = (String) messageJson.get("content");
                // Convert stored string back to MessageType enum.
                MessageType messageType = MessageType.valueOf((String) messageJson.get("messageType"));
                messages.add(new Message(senderUsername, receiverUsername, content, messageType));
            }
            System.out.println("Message data loaded from " + MESSAGES_FILE);
        } catch (IOException e) {
            // This is okay if file doesn't exist yet (first run of the app).
            System.out.println("No existing message data found for Part2, starting fresh: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error parsing message JSON file: " + e.getMessage());
        }
    }
}
