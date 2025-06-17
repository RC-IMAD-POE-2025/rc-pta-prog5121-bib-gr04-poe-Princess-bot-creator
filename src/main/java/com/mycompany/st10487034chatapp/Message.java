/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.st10487034chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.simple.JSONObject;

/**
 *
 * @author Princess
 */
public class Message {

    public String messageID;
    public int messageNumber;
    //public int messageCount;
    public String recipientCell;
    public String messageText;
   // private String senderId;

    public Message(int messageNumber) {
        this.messageID = String.valueOf(generateTenDigitNumber());
        this.messageNumber = messageNumber;

    }


    public long generateTenDigitNumber() {
        Random random = new Random();
        // Generate number between 1000000000 (inclusive) and 9999999999 (inclusive)
        return 1000000000L + (long) (random.nextDouble() * 9000000000L);
    }

    public boolean setMessage(String message) {
        if (checkMessage(message)) {
            this.messageText = message;
            return true;
        }
        return false;
    }

    public boolean checkMessage(String message) {
        if (!(message.length() <= 250)) {
            return false;
        }
        return true;

    }

    public boolean checkMessageID() {
        if (!(messageID.length() <= 10)) {
            return false;
        }
        return true;

    }

    public boolean setRecipientCell(String recipientCell) {
        if (checkRecipientCell(recipientCell)) {
            this.recipientCell = recipientCell;
            return true;
        }
        return false;
    }

    public boolean checkRecipientCell(String recipientCell) {
        if (!(recipientCell.length() <= 12)) {
            return false;
        } else if (!(recipientCell.startsWith("+"))) {
            return false;
        }
        return true;

    }

    public String createMessageHash() {
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].toUpperCase();

        String last = words[words.length - 1].toUpperCase();
        String hash = (messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
        return hash;
    }

    public String sentMessage(String action) {
        switch (action.toLowerCase()) {
            case "send message" -> {
                return "Message was successfullly sent";
            }
            case "discard message" -> {
                return "Press 1 to delete the message";
            }
            case "store message" -> {
                storeMessageToJson();
                return "Message was successfully stored";
            }

            default -> {
                return "Invalid action";
            }
        }
    }
   
    public void storeMessages(ArrayList<Message> messages) throws IOException {

        FileWriter file = new FileWriter("storeMessage.json", true);
        for (int i = 0; i < messages.size(); i++) {

            JSONObject obj = new JSONObject();
            obj.put("Message ID:", messages.get(i).messageID);
            obj.put("MessageHash:" , messages.get(i).createMessageHash());
            obj.put("RecipientCell:" , messages.get(i).recipientCell);
            obj.put("Message:" , messages.get(i).messageText);
            file.write(obj.toString() + System.lineSeparator());
        }
        file.close();
    }

    public String printMessage() {
        return "Message ID:" + messageID
                + "\nMessageHash:" + createMessageHash()
                + "\nRecipientCell:" + recipientCell
                + "\nMessage:" + messageText;

    }

    public int returnTotalMessages() {
        return ST10487034ChatApp.SentMessages.size();
    }

    public boolean storeMessageToJson() {
        System.out.println("Storing a message in JSON");
        return false;
    }
}
