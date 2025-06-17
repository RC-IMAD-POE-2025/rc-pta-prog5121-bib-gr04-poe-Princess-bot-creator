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
public class RegisterTest {
    
    public RegisterTest() {
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
     * Test of checkUsername method, of class Register.
     */
    @Test
    public void testCheckUsername() {
        
        Register user = new Register();
         user.checkUsername("kyl_1");
        ST10487034ChatApp.RegisteredUsers.add(user);
        System.out.println("checkUsername");
        Register instance = new Register();
        boolean expResult = true;
        boolean result = instance.checkUsername("kyl_1");
        assertEquals(expResult, result);
    }
@Test
    public void testCheckUsername_failedLength() {
        System.out.println("checkUsername");
        Register instance = new Register();
        
        boolean result = instance.checkUsername("kyl_");
        assertEquals(false, result);
        
        boolean resultMoreThan5 = instance.checkUsername("kyl_11");
        assertEquals(false, resultMoreThan5);
    }
     @Test
    public void testCheckUsername_failedUnderscore() {
        
        System.out.println("checkUsername");
        Register instance = new Register();
        boolean expResult = false;
        boolean result = instance.checkUsername("kyl!!!!!!!");
        assertEquals(expResult, result);
    }
    /**
     * Test of checkPassword method, of class Register.
     */
    @Test
    public void testCheckPassword() {
        System.out.println("checkPassword");
        Register instance = new Register();
        boolean expResult = true;
        boolean result = instance.checkPassword("Ch&&sec@ke99!");
        assertEquals(expResult, result);
    }
 @Test
    public void testCheckPassword_failedLength() {
        System.out.println("checkPassword");
        Register instance = new Register();
        boolean expResult = false;
        boolean result = instance.checkPassword("Ch&&sec");
        assertEquals(expResult, result);
    }
    @Test
    public void testCheckPassword_failedPattern() {
        System.out.println("checkPassword");
        Register instance = new Register();
        boolean expResult = false;
        boolean result = instance.checkPassword("password");
        assertEquals(expResult, result);
    }
    /**
     * Test of checkCellphone method, of class Register.
     */
    @Test
    public void testCheckCellphone() {
        System.out.println("checkCellphone");
        Register instance = new Register();
        boolean expResult = true;
        boolean result = instance.checkCellphone("+27838968976");
        assertEquals(expResult, result);
    }
@Test
    public void testCheckCellphone_failedCellphone() {
        System.out.println("checkCellphone");
        Register instance = new Register();
        boolean expResult = false;
        boolean result = instance.checkCellphone("08966553");
        assertEquals(expResult, result);
    }
    /**
     * Test of registerUser method, of class Register.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        Register instance = new Register();
        boolean expResult = true;
        boolean result = instance.registerUser("","","");
        assertEquals(expResult, result);
    }
    
}
