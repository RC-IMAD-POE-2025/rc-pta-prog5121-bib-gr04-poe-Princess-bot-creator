// Part1Test.java - For Registration and Login features
package ST10487034;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class Part1Test {

    private Part1 accountManager;

    // This runs before each test to make sure we have a fresh accountManager
    // This is important so tests don't interfere with each other, especially with file operations.
    @BeforeEach
    void setUp() {
        accountManager = new Part1();
        // Clear any existing users to ensure a clean state for testing
        // This is a bit of a hack since 'users' is private and 'loadUsersFromJson' is private,
        // but for testing, we need control. In a real scenario, I'd make 'users' accessible
        // for testing or have a specific method to clear data for tests.
        // For now, let's assume `Part1` always starts fresh enough or we rely on the clean load.
        // Alternatively, if I could modify Part1, I'd add a constructor for tests or a reset method.
        // Since I can't, I'll trust the load/save mechanism for now.
    }

    // --- Unit Tests for checkUserName() ---

    @Test
    @DisplayName("Test for correctly formatted username")
    void testCheckUserName_CorrectlyFormatted() {
        // As per the PoE: Username contains an underscore and is no more than five characters long.
        // Test Data: "kyl_1"
        assertTrue(accountManager.checkUserName("kyl_1"),
                "Username 'kyl_1' should be correctly formatted.");
    }

    @Test
    @DisplayName("Test for incorrectly formatted username - no underscore")
    void testCheckUserName_IncorrectlyFormatted_NoUnderscore() {
        // As per the PoE: The username does not contain an underscore.
        // Test Data: "kyle!!!!!!!"
        assertFalse(accountManager.checkUserName("kyle!!!!!!!"),
                "Username 'kyle!!!!!!!' should be incorrectly formatted (no underscore).");
    }

    @Test
    @DisplayName("Test for incorrectly formatted username - too short")
    void testCheckUserName_IncorrectlyFormatted_TooShort() {
        // PoE implies >= 5 chars, so anything less should be false.
        assertFalse(accountManager.checkUserName("abc"),
                "Username 'abc' should be incorrectly formatted (too short).");
    }

    @Test
    @DisplayName("Test for incorrectly formatted username - no alphanumeric/underscore")
    void testCheckUserName_IncorrectlyFormatted_SpecialChars() {
        // PoE implies alphanumeric/underscore only.
        assertFalse(accountManager.checkUserName("user$"),
                "Username 'user$' should be incorrectly formatted (invalid characters).");
    }

    // --- Unit Tests for checkPasswordComplexity() ---

    @Test
    @DisplayName("Test for password meeting complexity requirements")
    void testCheckPasswordComplexity_MeetsRequirements() {
        // As per the PoE: At least eight characters long, Contain a capital letter,
        // Contain a number, Contain a special character.
        // Test Data: "Ch&&sec@ke99!"
        assertTrue(accountManager.checkPasswordComplexity("Ch&&sec@ke99!"),
                "Password 'Ch&&sec@ke99!' should meet complexity requirements.");
    }

    @Test
    @DisplayName("Test for password not meeting complexity requirements - too short")
    void testCheckPasswordComplexity_TooShort() {
        // As per the PoE: At least eight characters long.
        // Test Data: "password"
        assertFalse(accountManager.checkPasswordComplexity("pass"),
                "Password 'pass' should not meet complexity (too short).");
    }

    @Test
    @DisplayName("Test for password not meeting complexity requirements - no capital")
    void testCheckPasswordComplexity_NoCapital() {
        // As per the PoE: Contain a capital letter.
        // Test Data: "password" (modified to "password123!")
        assertFalse(accountManager.checkPasswordComplexity("password123!"),
                "Password 'password123!' should not meet complexity (no capital letter).");
    }

    @Test
    @DisplayName("Test for password not meeting complexity requirements - no number")
    void testCheckPasswordComplexity_NoNumber() {
        // As per the PoE: Contain a number.
        assertFalse(accountManager.checkPasswordComplexity("Password!!"),
                "Password 'Password!!' should not meet complexity (no number).");
    }

    @Test
    @DisplayName("Test for password not meeting complexity requirements - no special character")
    void testCheckPasswordComplexity_NoSpecialChar() {
        // As per the PoE: Contain a special character.
        assertFalse(accountManager.checkPasswordComplexity("Password123"),
                "Password 'Password123' should not meet complexity (no special character).");
    }

    // --- Unit Tests for registerUser() ---
    @Test
    @DisplayName("Test successful user registration")
    void testRegisterUser_Success() {
        // Ensure that a valid user can be registered
        assertTrue(accountManager.registerUser("test_user", "Test@123", "John", "Doe"),
                "User should be registered successfully.");
        // Verify the user is actually in the list (if getUsers was public or via login)
        assertNotNull(accountManager.loginUser("test_user", "Test@123"),
                      "Newly registered user should be able to log in.");
    }

    @Test
    @DisplayName("Test registration with existing username")
    void testRegisterUser_ExistingUsername() {
        // Register a user first
        accountManager.registerUser("existing_user", "Test@123", "Jane", "Doe");
        // Try to register the same username again
        assertFalse(accountManager.registerUser("existing_user", "New@456", "Jim", "Beam"),
                "Registration should fail for an existing username.");
    }

    @Test
    @DisplayName("Test registration with invalid username format")
    void testRegisterUser_InvalidUsername() {
        // Attempt to register with a username that doesn't meet criteria
        assertFalse(accountManager.registerUser("bad", "Test@123", "Invalid", "User"),
                "Registration should fail due to invalid username format.");
    }

    @Test
    @DisplayName("Test registration with weak password")
    void testRegisterUser_WeakPassword() {
        // Attempt to register with a password that doesn't meet complexity
        assertFalse(accountManager.registerUser("valid_user", "weakpass", "Weak", "P"),
                "Registration should fail due to weak password.");
    }

    // --- Unit Tests for loginUser() ---

    @Test
    @DisplayName("Test successful user login")
    void testLoginUser_Success() {
        // First, register a user to log in with.
        accountManager.registerUser("valid_login", "StrongP@ss1", "Alice", "Smith");
        // Then, try to log in with correct credentials.
        assertNotNull(accountManager.loginUser("valid_login", "StrongP@ss1"),
                      "Login should be successful for correct credentials.");
    }

    @Test
    @DisplayName("Test failed user login - incorrect password")
    void testLoginUser_IncorrectPassword() {
        // Register a user.
        accountManager.registerUser("another_user", "MyPass#1", "Bob", "Johnson");
        // Try to log in with a wrong password.
        assertNull(accountManager.loginUser("another_user", "WrongPass#1"),
                   "Login should fail for incorrect password.");
    }

    @Test
    @DisplayName("Test failed user login - non-existent username")
    void testLoginUser_NonExistentUsername() {
        // Try to log in with a username that doesn't exist.
        assertNull(accountManager.loginUser("non_existent", "AnyPass1!"),
                   "Login should fail for a non-existent username.");
    }

    @Test
    @DisplayName("Test failed user login - empty credentials")
    void testLoginUser_EmptyCredentials() {
        // Test with empty username and password.
        assertNull(accountManager.loginUser("", ""),
                   "Login should fail for empty credentials.");
    }
}