import javax.swing.*;
import java.io.*;

public class ExportCSVDialog {
    public ExportCSVDialog(MarksheetManager manager) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                manager.exportToCSV(chooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(null, "CSV exported successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Export failed: " + ex.getMessage());
            }
        }
    }
}