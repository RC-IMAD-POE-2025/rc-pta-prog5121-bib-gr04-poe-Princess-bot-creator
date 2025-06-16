// Part3Test.java - For Report and Message Deletion
package ST10487034;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

public class Part3Test {

    private Part2 messageManager; // Part3 depends on Part2
    private Part3 reportManager;
    private static final String MESSAGES_FILE = "messages.json"; // Access the file name directly

    @BeforeEach
    void setUp() {
        // Clean up the JSON file before each test
        File file = new File(MESSAGES_FILE);
        if (file.exists()) {
            file.delete();
        }
        messageManager = new Part2(); // Fresh Part2 instance for each test
        reportManager = new Part3(messageManager); // Part3 needs the messageManager
    }

    // Helper method to populate messages for tests based on POE data
    private void populateMessagesForTests() {
        // Test Data Message 1 (Sent)
        messageManager.sendMessage("Developer", "+27834557896", "Did you get the cake?");
        // Test Data Message 2 (Stored/Drafted - using draftMessage as closest equivalent)
        messageManager.draftMessage("Developer", "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        // Test Data Message 3 (Disregarded - using draftMessage as closest equivalent for now, as no 'disregard' state/method exists)
        // Note: The POE has "Disregard" flag, but current Part2 only has SENT/DRAFT.
        // For this test, I'll use DRAFT for "Disregarded" as it's the closest non-sent state.
        messageManager.draftMessage("Developer", "+27834484567", "Yohoooo, I am at your gate.");
        // Test Data Message 4 (Sent)
        messageManager.sendMessage("Developer", "0838884567", "It is dinner time!");
        // Test Data Message 5 (Stored/Drafted)
        messageManager.draftMessage("Developer", "+27838884567", "Ok, I am leaving without you.");
    }

    // --- Unit Tests for displayAllMessages() ---

    @Test
    @DisplayName("Test displayAllMessages when no messages are available")
    void testDisplayAllMessages_NoMessages() {
        String expected = "No messages available to display.";
        assertEquals(expected, reportManager.displayAllMessages(),
                "Should return 'No messages available' when list is empty.");
    }

    @Test
    @DisplayName("Test displayAllMessages with populated messages")
    void testDisplayAllMessages_WithMessages() {
        populateMessagesForTests(); // Add some messages

        String report = reportManager.displayAllMessages();
        System.out.println("Display All Messages Report:\n" + report); // For manual verification during testing

        assertTrue(report.contains("--- All Messages ---"), "Report should have a title.");
        assertTrue(report.contains("Message 0:"), "Report should list messages with index 0.");
        assertTrue(report.contains("From: Developer"), "Report should show sender username.");
        assertTrue(report.contains("Content: Did you get the cake?"), "Report should contain content of message 1.");
        assertTrue(report.contains("Content: Where are you? You are late! I have asked you to be on time."), "Report should contain content of message 2.");
        assertTrue(report.contains("Content: It is dinner time!"), "Report should contain content of message 4.");
        assertTrue(report.contains("Type: SENT"), "Report should show SENT type for sent messages.");
        assertTrue(report.contains("Type: DRAFT"), "Report should show DRAFT type for drafted messages.");
    }

    // --- Unit Tests for searchMessagesBySender() ---
    // Note: The POE test data example for this method uses a recipient number "+27838884567"
    // but the actual method name is `searchMessagesBySender`. I will test with a sender username.

    @Test
    @DisplayName("Test searchMessagesBySender with existing sender")
    void testSearchMessagesBySender_ExistingSender() {
        populateMessagesForTests(); // All populated messages are from "Developer"
        String sender = "Developer";
        String report = reportManager.searchMessagesBySender(sender);
        System.out.println("Search by Sender Report (Developer):\n" + report); // For manual verification

        assertTrue(report.contains("--- Messages by Sender: Developer ---"), "Report should have a title for the sender.");
        assertTrue(report.contains("Content: Did you get the cake?"), "Report should contain messages from 'Developer'.");
        assertTrue(report.contains("Content: Where are you? You are late! I have asked you to be on time."), "Report should contain drafted messages from 'Developer'.");
        assertFalse(report.contains("No messages found for sender: Developer."), "Should find messages for 'Developer'.");
    }

    @Test
    @DisplayName("Test searchMessagesBySender with non-existent sender")
    void testSearchMessagesBySender_NonExistentSender() {
        populateMessagesForTests(); // Messages are from "Developer"
        String sender = "NonExistentUser";
        String report = reportManager.searchMessagesBySender(sender);
        System.out.println("Search by Sender Report (NonExistentUser):\n" + report); // For manual verification

        assertTrue(report.contains("No messages found for sender: NonExistentUser."), "Should report no messages found for non-existent sender.");
    }

    // --- Unit Tests for displayDraftedMessages() ---

    @Test
    @DisplayName("Test displayDraftedMessages when no drafts exist")
    void testDisplayDraftedMessages_NoDrafts() {
        messageManager.sendMessage("user1", "user2", "Only sent message."); // Send a message, but no drafts
        String expected = "--- Drafted Messages ---\nNo drafted messages available.\n";
        assertEquals(expected, reportManager.displayDraftedMessages(),
                "Should report no drafted messages when none exist.");
    }

    @Test
    @DisplayName("Test displayDraftedMessages with existing drafts")
    void testDisplayDraftedMessages_WithDrafts() {
        populateMessagesForTests(); // This populates messages including drafts
        String report = reportManager.displayDraftedMessages();
        System.out.println("Drafted Messages Report:\n" + report); // For manual verification

        assertTrue(report.contains("--- Drafted Messages ---"), "Report should have a title.");
        assertTrue(report.contains("Content: Where are you? You are late! I have asked you to be on time."), "Report should contain the first drafted message.");
        assertTrue(report.contains("Content: Yohoooo, I am at your gate."), "Report should contain the second drafted message.");
        assertTrue(report.contains("Content: Ok, I am leaving without you."), "Report should contain the third drafted message.");
        assertFalse(report.contains("No drafted messages available."), "Should find drafted messages.");
    }

    // --- Unit Tests for deleteMessage() ---
    // Note: The POE specifies "Delete a message using a message hash", but the implemented
    // method is `deleteMessage(int index)`. I will test based on the implemented method (index).

    @Test
    @DisplayName("Test deleteMessage successfully")
    void testDeleteMessage_Success() {
        populateMessagesForTests(); // Populates 5 messages (index 0-4)
        int initialSize = messageManager.getMessages().size(); // Should be 5

        // Delete the message at index 1 (the first drafted message)
        assertTrue(reportManager.deleteMessage(1), "Deletion should be successful for a valid index.");

        // Verify the size of the messages list decreased
        assertEquals(initialSize - 1, messageManager.getMessages().size(), "Message list size should decrease by 1.");

        // Verify the deleted message is no longer in the list
        String reportAfterDelete = reportManager.displayAllMessages();
        assertFalse(reportAfterDelete.contains("Content: Where are you? You are late! I have asked you to be on time."),
                "The deleted message should no longer be in the report.");
        assertTrue(reportAfterDelete.contains("Content: Did you get the cake?"), "First message should still be there.");
    }

    @Test
    @DisplayName("Test deleteMessage with invalid (out of bounds) index")
    void testDeleteMessage_InvalidIndex() {
        populateMessagesForTests(); // Populates 5 messages (index 0-4)
        int initialSize = messageManager.getMessages().size();

        // Try to delete an index that is too high
        assertFalse(reportManager.deleteMessage(99), "Deletion should fail for an invalid index.");
        assertEquals(initialSize, messageManager.getMessages().size(), "Message list size should remain unchanged.");

        // Try to delete a negative index
        assertFalse(reportManager.deleteMessage(-1), "Deletion should fail for a negative index.");
        assertEquals(initialSize, messageManager.getMessages().size(), "Message list size should remain unchanged.");
    }
}