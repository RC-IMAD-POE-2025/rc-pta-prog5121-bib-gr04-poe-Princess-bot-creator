package ST10487034;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class UnitTests {

    @Nested
    @DisplayName("Registration Logic Tests")
    class RegistrationTests {

        private Registration registrationLogic;
        
        private static final String VALID_FIRST_NAME = "Princess";
        private static final String VALID_LAST_NAME = "Molele";
        private static final String VALID_USERNAME = "pri_m";
        private static final String VALID_PASSWORD = "Password@1";
        private static final String VALID_CELLPHONE = "+27821234567";

        private static final String REGISTRATION_SUCCESS_OVERALL = "\nUser registered successfully!";
        private static final String REGISTRATION_FAILED_OVERALL = "\nUser registration failed. Please check the errors above.";

        @BeforeEach
        void setUp() {
            registrationLogic = new Registration();
        }

        @Test
        @DisplayName("Should succeed with all valid inputs")
        void testRegisterUser_AllValidInputs() {
            var result = registrationLogic.registerUser(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_USERNAME, VALID_PASSWORD, VALID_CELLPHONE);
            assertTrue(result.contains(REGISTRATION_SUCCESS_OVERALL));
        }
        
        @Test
        @DisplayName("Should fail with all invalid inputs and list all errors")
        void testRegisterUser_AllInvalidInputs() {
            var result = registrationLogic.registerUser("N1ame", "", "long_username", "pass", "123");
            assertTrue(result.contains(REGISTRATION_FAILED_OVERALL));
            assertEquals(6, result.size()); // 5 field errors + 1 overall status
        }

        // --- Edge Case Tests for Registration ---

        @Test
        @DisplayName("Should fail when username contains whitespace")
        void testRegisterUser_UsernameWithWhitespace() {
            var result = registrationLogic.registerUser(VALID_FIRST_NAME, VALID_LAST_NAME, " pri_m ", VALID_PASSWORD, VALID_CELLPHONE);
            assertTrue(result.contains("Username is not correctly formatted. Must be <= 5 chars and contain an underscore (_)."));
        }
        
        @Test
        @DisplayName("Should succeed when password is exactly 8 characters")
        void testRegisterUser_PasswordAtBoundary() {
            var result = registrationLogic.registerUser(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_USERNAME, "Pass@123", VALID_CELLPHONE);
            assertTrue(result.contains(REGISTRATION_SUCCESS_OVERALL));
        }
        
        @Test
        @DisplayName("Should fail for a first name containing a hyphen")
        void testRegisterUser_NameWithHyphen() {
            var result = registrationLogic.registerUser("Mary-Jane", VALID_LAST_NAME, VALID_USERNAME, VALID_PASSWORD, VALID_CELLPHONE);
            assertTrue(result.contains("Invalid first name. Must contain only letters."));
        }
        
        @Test
        @DisplayName("Should fail gracefully when inputs are null")
        void testRegisterUser_NullInputs() {
            var result = registrationLogic.registerUser(null, null, null, null, null);
            assertTrue(result.contains(REGISTRATION_FAILED_OVERALL));
            assertEquals(6, result.size());
        }
    }

    @Nested
    @DisplayName("Login Logic Tests")
    class LoginTests {
        
        private Login loginLogic;
        private Registration registeredUser;

        @BeforeEach
        void setUp() {
            loginLogic = new Login();
            registeredUser = new Registration();
            // Create a valid user to test against
            registeredUser.registerUser("Princess", "Molele", "pri_m", "Password@1", "+27821234567");
        }

        @Test
        @DisplayName("Should return true for correct credentials")
        void testLoginUser_CorrectCredentials() {
            assertTrue(loginLogic.loginUser("pri_m", "Password@1", registeredUser));
        }

        @Test
        @DisplayName("Should return false for incorrect password")
        void testLoginUser_IncorrectPassword() {
            assertFalse(loginLogic.loginUser("pri_m", "wrong_password", registeredUser));
        }

        @Test
        @DisplayName("Should return false for incorrect username")
        void testLoginUser_IncorrectUsername() {
            assertFalse(loginLogic.loginUser("wrong_user", "Password@1", registeredUser));
        }
        
        @Test
        @DisplayName("Should return welcome message after successful login")
        void testReturnLoginStatus_OnSuccess() {
            loginLogic.loginUser("pri_m", "Password@1", registeredUser);
            assertEquals("Welcome Princess Molele!", loginLogic.returnLoginStatus(registeredUser));
        }
        
        @Test
        @DisplayName("Should return error message after failed login")
        void testReturnLoginStatus_OnFailure() {
            loginLogic.loginUser("pri_m", "wrong_password", registeredUser);
            assertEquals("Username or password incorrect. Please try again.", loginLogic.returnLoginStatus(registeredUser));
        }
        
        // --- Edge Case Tests for Login ---
        
        @Test
        @DisplayName("Should fail for case-sensitive username mismatch")
        void testLoginUser_CaseSensitiveUsername() {
            assertFalse(loginLogic.loginUser("Pri_m", "Password@1", registeredUser));
        }
        
        @Test
        @DisplayName("Should fail for case-sensitive password mismatch")
        void testLoginUser_CaseSensitivePassword() {
            assertFalse(loginLogic.loginUser("pri_m", "password@1", registeredUser));
        }
        
        @Test
        @DisplayName("Should fail gracefully for null inputs")
        void testLoginUser_NullInputs() {
            assertFalse(loginLogic.loginUser(null, null, registeredUser));
        }
    }

    @Nested
    @DisplayName("Message & Reports Logic Tests")
    class MessageAndReportsTests {
        
        private Reports reportsLogic;
        private Message message;
        
        @BeforeEach
        void setUp() {
            reportsLogic = new Reports();
            message = new Message("Sender", "+27111222333", "Test payload", 0);
        }

        @Test
        @DisplayName("Should create correct hash for single-word payload")
        void testCreateMessageHash_SingleWordPayload() {
            String hash = message.createMessageHash("12345", 1, "Hello");
            assertEquals("12:1:HELLOHELLO", hash);
        }
        
        @Test
        @DisplayName("Should create correct hash and ignore extra whitespace")
        void testCreateMessageHash_WithExtraWhitespace() {
            String hash = message.createMessageHash("12345", 1, "   Hello   there   ");
            assertEquals("12:1:HELLOTHERE", hash);
        }
        
        @Test
        @DisplayName("Should invalidate recipient number with whitespace")
        void testValidateRecipientNumber_WithWhitespace() {
            String result = message.validateRecipientNumber("+27111222333 ");
            assertTrue(result.contains("incorrectly formatted"));
        }
    }
}