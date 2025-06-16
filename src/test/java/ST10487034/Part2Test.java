// Part2Test.java - For Sending and Drafting Messages
package ST10487034;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

public class Part2Test {

    private Part2 messageManager;
    private static final String MESSAGES_FILE = "messages.json"; // Access the file name directly

    @BeforeEach
    void setUp() {
        // Clean up the JSON file before each test to ensure a fresh state
        File file = new File(MESSAGES_FILE);
        if (file.exists()) {
            file.delete();
        }
        messageManager = new Part2(); // This will load from a fresh or empty file
    }

    // --- Unit Tests for sendMessage() ---

    @Test
    @DisplayName("Test sending a message successfully")
    void testSendMessage_Success() {
        String sender = "sender1_user";
        String receiver = "receiver1_user";
        String content = "Hello, this is a test message.";

        messageManager.sendMessage(sender, receiver, content);

        // Verify that the message was added to the internal list
        ArrayList<Part2.Message> messages = messageManager.getMessages();
        assertFalse(messages.isEmpty(), "Message list should not be empty after sending.");
        assertEquals(1, messages.size(), "There should be one message in the list.");

        Part2.Message sentMessage = messages.get(0);
        assertEquals(sender, sentMessage.getSenderUsername(), "Sender username should match.");
        assertEquals(receiver, sentMessage.getReceiverUsername(), "Receiver username should match.");
        assertEquals(content, sentMessage.getContent(), "Message content should match.");
        assertEquals(Part2.MessageType.SENT, sentMessage.getMessageType(), "Message type should be SENT.");
    }

    @Test
    @DisplayName("Test sending multiple messages")
    void testSendMessage_MultipleMessages() {
        messageManager.sendMessage("senderA", "receiverX", "First message.");
        messageManager.sendMessage("senderB", "receiverY", "Second message.");

        ArrayList<Part2.Message> messages = messageManager.getMessages();
        assertEquals(2, messages.size(), "There should be two messages in the list after sending multiple.");

        // Check content of the second message to ensure order
        Part2.Message secondMessage = messages.get(1);
        assertEquals("senderB", secondMessage.getSenderUsername());
        assertEquals("receiverY", secondMessage.getReceiverUsername());
        assertEquals("Second message.", secondMessage.getContent());
        assertEquals(Part2.MessageType.SENT, secondMessage.getMessageType());
    }

    // --- Unit Tests for draftMessage() ---

    @Test
    @DisplayName("Test drafting a message successfully")
    void testDraftMessage_Success() {
        String sender = "drafter_user";
        String receiver = ""; // Receiver can be empty for drafts
        String content = "This is a draft message to be sent later.";

        messageManager.draftMessage(sender, receiver, content);

        // Verify that the draft was added to the internal list
        ArrayList<Part2.Message> messages = messageManager.getMessages();
        assertFalse(messages.isEmpty(), "Message list should not be empty after drafting.");
        assertEquals(1, messages.size(), "There should be one message (draft) in the list.");

        Part2.Message draftedMessage = messages.get(0);
        assertEquals(sender, draftedMessage.getSenderUsername(), "Sender username should match for draft.");
        assertEquals(receiver, draftedMessage.getReceiverUsername(), "Receiver username can be empty for draft.");
        assertEquals(content, draftedMessage.getContent(), "Draft content should match.");
        assertEquals(Part2.MessageType.DRAFT, draftedMessage.getMessageType(), "Message type should be DRAFT.");
    }

    @Test
    @DisplayName("Test sending and drafting messages together")
    void testMixedMessages() {
        messageManager.sendMessage("user1", "user2", "Hey there!");
        messageManager.draftMessage("user3", "", "Meeting notes.");
        messageManager.sendMessage("user2", "user1", "Got your message!");

        ArrayList<Part2.Message> messages = messageManager.getMessages();
        assertEquals(3, messages.size(), "There should be three messages (sent and drafted) in total.");

        // Verify specific message types and content
        assertEquals(Part2.MessageType.SENT, messages.get(0).getMessageType());
        assertEquals("Hey there!", messages.get(0).getContent());

        assertEquals(Part2.MessageType.DRAFT, messages.get(1).getMessageType());
        assertEquals("Meeting notes.", messages.get(1).getContent());

        assertEquals(Part2.MessageType.SENT, messages.get(2).getMessageType());
        assertEquals("Got your message!", messages.get(2).getContent());
    }

    // Although the POE mentioned specific return messages for "MessageSent"
    // and "Message exceeding 250 characters", the sendMessage/draftMessage methods
    // are void. I'm testing the state change (message added to list) instead.
    // Also, message length validation and recipient number validation were mentioned in POE
    // but no explicit methods were found in Part2 for direct unit testing of these
    // outside of the UI input validation. I am focusing on the core Part2 functionalities.
}