package ST10487034;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This class handles all user account related operations, including
// registration, login, and saving/loading user data.
public class Part1 {

    // Inner class to represent a User.
    // This keeps the user definition closely tied to where it's used.
    public static class User {
        private String username;
        private String password;
        private String firstName;
        private String lastName;

        public User(String username, String password, String firstName, String lastName) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }

        @Override
        public String toString() {
            return "Username: " + username + ", Name: " + firstName + " " + lastName;
        }
    }

    // List to hold all registered users.
    private ArrayList<User> users;
    // File where user data will be stored.
    private static final String USERS_FILE = "users.json";

    public Part1() {
        users = new ArrayList<>();
        // Try to load existing users when this class is created.
        loadUsersFromJson();
    }

    // Gets the list of users. Useful for testing or debugging.
    public ArrayList<User> getUsers() {
        return users;
    }

    // Registers a new user. Returns true if successful, false otherwise.
    public boolean registerUser(String username, String password, String firstName, String lastName) {
        // Check if username is already taken.
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("Registration failed: Username already exists.");
                return false;
            }
        }

        // Validate username format using POE rules.
        if (!checkUserName(username)) {
            System.out.println("Registration failed: Username does not meet criteria (>= 5 chars, alphanumeric/underscore).");
            return false;
        }

        // Validate password complexity using POE rules.
        if (!checkPasswordComplexity(password)) {
            System.out.println("Registration failed: Password does not meet complexity requirements (>= 8 chars, 1 capital, 1 number, 1 special).");
            return false;
        }

        // If all checks pass, create and add the new user.
        User newUser = new User(username, password, firstName, lastName);
        users.add(newUser);
        saveUsersToJson(); // Save changes to file.
        System.out.println("User '" + username + "' registered successfully.");
        return true;
    }

    // Checks if the username follows the rules: >= 5 characters, alphanumeric and underscore only.
    public boolean checkUserName(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{5,}$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    // Checks if the password follows the rules: >= 8 characters, at least one capital letter,
    // one number, and one special character.
    public boolean checkPasswordComplexity(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Attempts to log in a user. Returns the User object if credentials are correct, null otherwise.
    public User loginUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                System.out.println("Login successful for user: " + username);
                return u; // Return the user object if found.
            }
        }
        System.out.println("Login failed: Invalid username or password.");
        return null; // No matching user found.
    }

    // Saves the current list of users to a JSON file for persistence.
    @SuppressWarnings("unchecked")
    private void saveUsersToJson() {
        JSONArray userList = new JSONArray();
        for (User user : users) {
            JSONObject userObject = new JSONObject();
            userObject.put("username", user.getUsername());
            userObject.put("password", user.getPassword());
            userObject.put("firstName", user.getFirstName());
            userObject.put("lastName", user.getLastName());
            userList.add(userObject);
        }

        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(userList.toJSONString());
            file.flush();
            System.out.println("User data saved to " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving users to JSON: " + e.getMessage());
        }
    }

    // Loads user data from the JSON file when the application starts.
    private void loadUsersFromJson() {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Object obj = parser.parse(reader);
            JSONArray userList = (JSONArray) obj;
            users.clear(); // Clear existing list before loading from file.

            for (Object userObj : userList) {
                JSONObject userJson = (JSONObject) userObj;
                String username = (String) userJson.get("username");
                String password = (String) userJson.get("password");
                String firstName = (String) userJson.get("firstName");
                String lastName = (String) userJson.get("lastName");
                users.add(new User(username, password, firstName, lastName));
            }
            System.out.println("User data loaded from " + USERS_FILE);
        } catch (IOException e) {
            // This is okay if file doesn't exist yet (first run of the app).
            System.out.println("No existing user data found for Part1, starting fresh: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Error parsing user JSON file: " + e.getMessage());
        }
    }
}
