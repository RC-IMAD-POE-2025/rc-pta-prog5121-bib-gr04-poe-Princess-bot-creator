/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.st10487034chatapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Princess
 */
public class Register {

    public String registeredUsername;
    public String registeredPassword;
    public String registeredCellphone;

    public boolean checkUsername(String username) {

       return CommonValidator.checkUsername(username);
    }

    public boolean checkPassword(String password) {

       return  CommonValidator.checkPassword(password);
    }

    public boolean checkCellphone(String cellphone) {
        String mobileNumberRegex = "^\\+27(6\\d|7\\d|8[1-4])\\d{7}$";
        Pattern pattern = Pattern.compile(mobileNumberRegex);

        Matcher matcher = pattern.matcher(cellphone);
        return matcher.matches();
    }

    public boolean registerUser(String username, String password, String cellphone) {
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellphone = cellphone;
        return true;

    }
}
