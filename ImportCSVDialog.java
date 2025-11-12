import javax.swing.*;
import java.io.*;

public class ImportCSVDialog {
    public ImportCSVDialog(MarksheetManager manager, JTextArea outputArea) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                manager.importFromCSV(chooser.getSelectedFile().getAbsolutePath());
                outputArea.setText("CSV imported successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Import failed: " + ex.getMessage());
            }
        }
    }
}