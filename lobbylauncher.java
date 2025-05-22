import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LobbyLauncher {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set look and feel: " + e.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            LobbyGUI gui = new LobbyGUI();
            gui.setVisible(true);
        });
    }
}