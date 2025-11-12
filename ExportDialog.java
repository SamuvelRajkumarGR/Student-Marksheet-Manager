import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.io.File;
import java.awt.Desktop;

public class ExportDialog {
    public ExportDialog(MarksheetManager manager) {
        // Prompt for class and exam name
        String className = JOptionPane.showInputDialog(null, "Enter Class Name:");
        String examName = JOptionPane.showInputDialog(null, "Enter Exam Name:");

        if (className == null || examName == null || className.isEmpty() || examName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Export cancelled: Class or Exam name missing.");
            return;
        }

        manager.setClassName(className);
        manager.setExamName(examName);

        // File chooser for PDF export
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".pdf")) {
                path += ".pdf";
            }

            try {
                manager.exportToPDF(path);
                JOptionPane.showMessageDialog(null, "Exported to PDF successfully.");

                // Open the PDF automatically
                File pdfFile = new File(path);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Export failed: " + ex.getMessage());
            }
        }
    }
}