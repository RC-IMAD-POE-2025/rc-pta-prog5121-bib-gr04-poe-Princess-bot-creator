/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.st10487034chatapp;

import java.util.ArrayList;

/**
 *
 * @author Princess
 */
public class ST10487034ChatApp {

    public static ArrayList<Register> RegisteredUsers = new ArrayList<>();
    public static ArrayList<Message> SentMessages = new ArrayList<>();
    public static ArrayList<Message> DicardedMessages = new ArrayList<>();
    public static ArrayList<Message> StoredMessages = new ArrayList<>();
    public static ArrayList<String> MessageHashes = new ArrayList<>();
    public static Login LoginUser = new Login();
    
    /*public void InitializeParallelArrays(int numMessages){
        this.RegisteredUsers = new ArrayList<>(numMessages);
        this.SentMessages = new ArrayList<>(numMessages);
        this.DicardedMessages = new ArrayList<>(numMessages);
        this.StoredMessages = new ArrayList<>(numMessages);
        this.MessageHashes = new ArrayList<>(numMessages);
    }*/

    public static void main(String[] args) {
        System.out.println("Hello World!");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterForm().setVisible(true);
            }
        });
    }
}
