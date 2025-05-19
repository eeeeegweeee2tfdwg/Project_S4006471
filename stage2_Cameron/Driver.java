package stage2_Cameron;

import javax.swing.SwingUtilities;

public class Driver {
    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserInterface();
            }
        });
    }
}