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
public class LoginTest {

    public LoginTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    /*@BeforeAll
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
    }*/
    /**
     * Test of loginUser method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testLoginUser() {
        //Setup
        Register user = new Register();
         user.registerUser("kyl_1", "Ch&&sec@ke99!", "");
        ST10487034ChatApp.RegisteredUsers.add(user);
        
        
        System.out.println("loginUser");
        Login instance = new Login();
        
        //act
        boolean result = instance.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals(true, result);
    }

    @org.junit.jupiter.api.Test
    public void testLoginUser_failedlogin() {
        Register user = new Register();
         user.registerUser("kyl_1", "Ch&&sec@ke99!", "");
        ST10487034ChatApp.RegisteredUsers.add(user);
        System.out.println("loginUser");
        Login instance = new Login();
        boolean result = instance.loginUser("kyl_2", "Ch&&sec@ke99!");
        assertEquals(false, result);
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @org.junit.jupiter.api.Test
    public void testReturnLoginStatus() {
        Login instance = new Login(); // Create instance of a class and initialize
        boolean result = instance.returnLoginStatus("dipelo"); //Call a method (returnLoginStatus) from Login class

        assertEquals(false, result);
    }

}
