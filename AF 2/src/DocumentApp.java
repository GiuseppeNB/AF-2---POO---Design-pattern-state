import javax.swing.SwingUtilities;
import view.DocumentView;

public class DocumentApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DocumentView view = new DocumentView();
            view.setVisible(true);
        });
    }
}