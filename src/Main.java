import javax.swing.SwingUtilities;
import ui.AgendaControllerFx;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgendaControllerFx::new);
    }
}
