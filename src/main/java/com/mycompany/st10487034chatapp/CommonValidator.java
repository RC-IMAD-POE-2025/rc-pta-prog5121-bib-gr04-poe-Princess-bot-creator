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
public final class CommonValidator {

    public static boolean checkUsername(String username) {

        if (!username.contains("_")) {
            return false;
        }
        if (!(username.length() == 5)) {
            return false;
        }

        return true;
    }

    public static boolean checkPassword(String password) {

        if (!(password.length() >= 8)) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
