package ST10487034;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration {

    // Private fields to store user details
    private String _username;
    private String _password;
    private String _cellphone;
    private String _firstname;
    private String _lastname;

    // Getters for user details
    public String getUsername() { return _username; }
    public String getPassword() { return _password; }
    public String getCellphone() { return _cellphone; }
    public String getFirstName() { return _firstname; }
    public String getLastName() { return _lastname; }

    /**
     * Checks if the username contains an underscore and is no more than 5 characters long.
     * @param username The username to validate.
     * @return true if the username is valid, false otherwise.
     */
    private boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks if the password meets the complexity requirements.
     * @param password The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    private boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        return password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[!@#$%^&*()\\-_+=<>?].*");
    }
    
    /**
     * Validates a South African cellphone number using regex.
     * Must start with +27 and be followed by 9 digits.
     * @param cellPhone The cellphone number to validate.
     * @return true if the format is correct, false otherwise.
     */
    private boolean checkCellphone(String cellPhone) {
        if (cellPhone == null) return false;
        String regex = "^\\+27[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellPhone);
        return matcher.matches();
    }

    /**
     * Checks if a name is a natural name (no special characters or numbers).
     * @param name The name to check.
     * @return true if the name is valid, false otherwise.
     */
    private boolean checkNaturalName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[a-zA-Z]+");
    }

    /**
     * Registers a new user if all validation checks pass.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param userName The user's chosen username.
     * @param password The user's chosen password.
     * @return A list of success or error messages.
     */
    public ArrayList<String> registerUser(String firstName, String lastName, String userName, String password, String cellphone) {
        ArrayList<String> results = new ArrayList<>();
        boolean allValid = true;

        if (!checkNaturalName(firstName)) {
            results.add("Invalid first name. Must contain only letters.");
            allValid = false;
        } else { results.add("First name captured successfully."); }

        if (!checkNaturalName(lastName)) {
            results.add("Invalid last name. Must contain only letters.");
            allValid = false;
        } else { results.add("Last name captured successfully."); }
        
        if (!checkUserName(userName)) {
            results.add("Username is not correctly formatted. Must be <= 5 chars and contain an underscore (_).");
            allValid = false;
        } else { results.add("Username successfully captured."); }

        if (!checkPasswordComplexity(password)) {
            results.add("Password does not meet complexity requirements.");
            allValid = false;
        } else { results.add("Password successfully captured."); }
        
        if (!checkCellphone(cellphone)) {
            results.add("Invalid cellphone number. Must be in the format +27XXXXXXXXX.");
            allValid = false;
        } else { results.add("Cellphone number successfully captured."); }

        if (allValid) {
            this._firstname = firstName;
            this._lastname = lastName;
            this._username = userName;
            this._password = password;
            this._cellphone = cellphone;
            results.add("\nUser registered successfully!");
        } else {
            results.add("\nUser registration failed. Please check the errors above.");
        }
        
        return results;
    }
}