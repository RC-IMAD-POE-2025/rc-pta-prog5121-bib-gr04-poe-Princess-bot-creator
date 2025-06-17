/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.st10487034chatapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Princess
 */
public class QuickchatTest {
    
    public QuickchatTest() {
    }
    /*
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
*/
    /**
     * Test of checkMessage method, of class QuickchatApp.
     */
    @Test
    public void testCheckMessage() {
        System.out.println("checkMessage");
        Message instance = new Message(1);
        boolean expResult = true;
        boolean result = instance.checkMessage("Hi Mike,can you join us for dinner");
        assertEquals(expResult, result);   
    }
@Test
    public void testCheckMessage_failedMessage() {
        System.out.println("checkMessage");
        Message instance = new Message(1);
        boolean expResult = false;
        boolean result = instance.checkMessage("The stars shimmered gently above the quiet village as a soft breeze rustled the leaves. In the distance, an owl hooted, its call echoing through the trees. Everything seemed still, yet alive, as if nature itself was holding its breath in peaceful anticipation of the coming dawn.");
        assertEquals(expResult, result);
       
    }
    /**
     * Test of checkMessageID method, of class QuickchatApp.
     */
    @Test
    public void testCheckMessageID() {
        System.out.println("checkMessageID");
        Message instance = new Message(1);
        boolean expResult = true;
        boolean result = instance.checkMessageID();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of checkRecipientCell method, of class QuickchatApp.
     */
    @Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");
        Message instance = new Message(1);
        boolean expResult = true;
        boolean result = instance.checkRecipientCell("+27718693002");
        assertEquals(expResult, result);
        
    }
@Test
    public void testCheckRecipientCell_failedLength() {
        System.out.println("checkRecipientCell");
        Message instance = new Message(1);
        boolean expResult = false;
        boolean result = instance.checkRecipientCell("2771869300");
        assertEquals(expResult, result);
        
    }
    @Test
    public void testCheckRecipientCell_failedCode() {
        System.out.println("checkRecipientCell");
        Message instance = new Message(1);
        boolean expResult = false;
        boolean result = instance.checkRecipientCell("071869300");
        assertEquals(expResult, result);
        
    }
    
    /**
     * Test of createMessaheHash method, of class QuickchatApp.
     */
    @Test
    public void testCreateMessaheHash() {
        System.out.println("createMessaheHash");
        Message instance = new Message(1);
        String expResult = "1:HELLOTEST";
        instance.messageText = "Hello world test";
        //1:HELLOTEST
        String result = instance.createMessageHash();
        assertTrue(result.contains(expResult));
        
    }

    /**
     * Test of sentMessage method, of class QuickchatApp.
     */
    @Test
    public void testSentMessage() {
        System.out.println("sentMessage");
        Message instance = new Message(1);
        String expResult = "Message was successfullly sent";
        String result = instance.sentMessage("send message");
        assertEquals(expResult, result);
        
    }

    /**
     * Test of printMessage method, of class QuickchatApp.
     */
    @Test
    public void testPrintMessage() {
        System.out.println("printMessage");
        Message instance = new Message(1);
        boolean expResult = false;
        instance.messageText = "hello world";
       String result = instance.printMessage();
        
         assertTrue(result.contains("Message ID"));
        assertTrue(result.contains("Message:hello world"));
    }

    /**
     * Test of returnTotalMessages method, of class QuickchatApp.
     */
    @Test
    public void testReturnTotalMessages() {
        System.out.println("returnTotalMessages");
        Message instance = new Message(1);
      int  expResult = 0;
        int result = instance.returnTotalMessages();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of storeMessage method, of class QuickchatApp.
     */
    /*
    @Test
    public void testStoreMessage() {
        System.out.println("storeMessage");
        Message instance = new Message(1);
        boolean expResult = false;
        boolean result = instance.storeMessage();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of displayLongestMessage method, of class QuickchatApp.
     
    @Test
    public void testDisplayLongestMessage() {
        System.out.println("displayLongestMessage");
        Quickchat instance = new Quickchat();
        boolean expResult = false;
        boolean result = instance.displayLongestMessage();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of searchMessage method, of class QuickchatApp.
     
    @Test
    public void testSearchMessage() {
        System.out.println("searchMessage");
        Quickchat instance = new Quickchat();
        boolean expResult = false;
        boolean result = instance.searchMessage();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of deleteMessage method, of class QuickchatApp.
     
    @Test
    public void testDeleteMessage() {
        System.out.println("deleteMessage");
        Quickchat instance = new Quickchat();
        boolean expResult = false;
        boolean result = instance.deleteMessage();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of displayReport method, of class QuickchatApp.
     
    @Test
    public void testDisplayReport() {
        System.out.println("displayReport");
        Quickchat instance = new Quickchat();
        boolean expResult = false;
        boolean result = instance.displayReport();
        assertEquals(expResult, result);
        
    */
}

