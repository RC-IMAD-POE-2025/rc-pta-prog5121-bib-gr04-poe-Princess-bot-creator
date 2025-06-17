package ST10487034;

import java.util.Objects;

public class Login {

    private boolean loginSuccess = false;

    public boolean loginUser(String usernameAttempt, String passwordAttempt, Registration registeredUser) {
     
        if (registeredUser == null) {
            loginSuccess = false;
            return false;
        }
        loginSuccess = Objects.equals(usernameAttempt, registeredUser.getUsername()) && 
                       Objects.equals(passwordAttempt, registeredUser.getPassword());
        return loginSuccess;
    }

    public String returnLoginStatus(Registration registeredUser) {
        if (loginSuccess) {
            return "Welcome " + registeredUser.getFirstName() + " " + registeredUser.getLastName() + "!";
        } else {
            return "Username or password incorrect. Please try again.";
        }
    }
}