package ST10487034;

import java.security.SecureRandom;

public class Message {

    // Attributes
    private String messageID;
    private String messageHash;
    private String sender;
    private String recipientNumber;
    private String payload;
    private int messageIndex;

    private static int messageCounter = 0; // Static counter for all messages

    // Constructor
    public Message(String sender, String recipientNumber, String payload, int index) {
        this.messageID = generateRandomId();
        this.sender = sender;
        this.recipientNumber = recipientNumber;
        this.payload = payload;
        this.messageIndex = index;
        this.messageHash = createMessageHash(this.messageID, this.messageIndex, this.payload);
    }
    
    // Getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getSender() { return sender; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getPayload() { return payload; }

    /**
     * Generates a random 10-digit number as a string.
     * @return A random 10-digit ID.
     */
    private String generateRandomId() {
        SecureRandom random = new SecureRandom();
        long number = 1_000_000_000L + random.nextLong() % 9_000_000_000L;
        return String.valueOf(Math.abs(number));
    }

    /**
     * Validates that the message payload is 250 characters or less.
     * @param payload The message text.
     * @return A success message or an error message with the character overflow.
     */
    public String validatePayloadLength(String payload) {
        if (payload.length() <= 250) {
            return "Message ready to send.";
        } else {
            int over = payload.length() - 250;
            return "Message exceeds 250 characters by " + over + ", please reduce size.";
        }
    }

    /**
     * Validates that the recipient number is a valid South African number (+27XXXXXXXXX).
     * @param recipient The recipient's cellphone number.
     * @return A success or failure message.
     */
    public String validateRecipientNumber(String recipient) {
        if (recipient != null && recipient.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    /**
     * Creates a specific hash from message data.
     * Format: (first two digits of ID):(index):(first word):(last word)
     * @param id The message ID.
     * @param index The message index.
     * @param payload The message text.
     * @return The formatted hash in uppercase.
     */
    public String createMessageHash(String id, int index, String payload) {
        if (id == null || id.length() < 2 || payload == null || payload.trim().isEmpty()) {
            return "INVALID:DATA";
        }
        String[] words = payload.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String idPart = id.substring(0, 2);

        return (idPart + ":" + index + ":" + firstWord + lastWord).toUpperCase();
    }
    
    /**
     * Increments the static message counter.
     */
    public void sendMessage() {
        messageCounter++;
    }

    /**
     * Returns the total number of messages sent.
     * @return The static message counter.
     */
    public static int returnTotalMessages() {
        return messageCounter;
    }
}