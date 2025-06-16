package ST10487034;

import javax.swing.*;

// This is the main application class for the QuickChatApp project.
// It serves as the entry point, launching the LoginFrontEnd.
public class PrincessPOE extends JFrame { // Removed ActionListener as it's just a launcher now

    // Constructor for the main application frame.
    public PrincessPOE() {
        // Basic frame setup, but it won't be visible itself.
        setTitle("QuickChat POE - Princess");
        setSize(100, 100); // Very small window as it just launches another
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // This frame doesn't need to be visible, it just triggers the login form.
         setVisible(true); // Can uncomment this if a splash screen is desired
    }

    // Main method to start the application.
    public static void main(String[] args) {
        // Ensure GUI updates are performed on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            // No need to create PrincessPOE instance if it's just launching LoginFrontEnd.
            // Just directly launch the LoginFrontEnd.
            LoginFrontEnd loginForm = new LoginFrontEnd();
            loginForm.setVisible(true);
  
        });
    }
}
