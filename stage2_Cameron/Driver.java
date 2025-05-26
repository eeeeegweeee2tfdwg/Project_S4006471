package stage2_Cameron;

import javax.swing.SwingUtilities;

/**
 * The main driver class for the MelLibrary application.
 * This class's primary responsibility is to create and launch the
 * UserInterface.
 */
public class Driver {

    /**
     * The main method, which is the entry point of the application.
     * It instantiates the UserInterface and makes it visible.
     * 
     * @param args Command line arguments (not used in this application).
     */
    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) {
        // Ensure the GUI is created and updated on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of the UserInterface, which sets up and displays the GUI.
                new UserInterface();
            }
        });

        // You could add some command-line interaction here if you wanted,
        // but for a GUI application, launching the UI is usually the main task.
        System.out.println("MelLibrary Application Started.");
    }
}