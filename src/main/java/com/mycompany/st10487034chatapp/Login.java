/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.st10487034chatapp;

/**
 *
 * @author Princess
 */
public class Login {

    /*   private String registeredUsername = "kyl_1";
    private String registeredPassword= "Ch&&sec@ke99!";
   private boolean isLoggedin = false;
     */
    private Register loggedInUser;
    private boolean isLoggedin = false;
    
    public boolean loginUser(String username, String password) {
        isLoggedin = false;
        for (Register registeredUser : ST10487034ChatApp.RegisteredUsers) {
            if (username.equals(registeredUser.registeredUsername) && password.equals(registeredUser.registeredPassword)) {
                loggedInUser = registeredUser;
                isLoggedin = true;
                return true;
            }
        }
        return false;
    }

    public Register getLoggedinUser() {
        return loggedInUser;
    }

    public boolean returnLoginStatus(String username) {
        return isLoggedin;
    }

}
