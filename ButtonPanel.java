import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ButtonPanel extends JPanel {
    public ButtonPanel(MarksheetManager manager, JTextArea outputArea) {
        setLayout(new GridLayout(4, 5, 10, 10)); // Adjusted for 12 buttons

        JButton addBtn = new JButton("Add Student");
        JButton searchBtn = new JButton("Search by Roll");
        JButton nameSearchBtn = new JButton("Search by Name");
        JButton chartBtn = new JButton("Show Chart");
        JButton importBtn = new JButton("Import CSV");
        JButton exportBtn = new JButton("Export CSV");
        JButton printAllBtn = new JButton("Print All Records");
        JButton updateBtn = new JButton("Update Student");
        JButton exportButton = new JButton("Export to PDF");
	JButton exportAllToSinglePDFBtn = new JButton("Export All to PDF");
	add(exportAllToSinglePDFBtn);
        add(addBtn); add(searchBtn); add(nameSearchBtn);
	add(chartBtn);
        add(importBtn); add(exportBtn); add(printAllBtn);
        add(updateBtn); add(exportButton);

        addBtn.addActionListener(e -> {
            new AddStudentDialog(manager, outputArea);
            ((MarksheetGUI) SwingUtilities.getWindowAncestor(this)).updateDashboard();
        });

        searchBtn.addActionListener(e -> new SearchDialog(manager, outputArea));
        nameSearchBtn.addActionListener(e -> new NameSearchDialog(manager, outputArea));

        chartBtn.addActionListener(e -> new ChartWindow(manager.getGradeDistribution()));

        importBtn.addActionListener(e -> {
            new ImportCSVDialog(manager, outputArea);
            ((MarksheetGUI) SwingUtilities.getWindowAncestor(this)).updateDashboard();
        });

        exportBtn.addActionListener(e -> new ExportCSVDialog(manager));

        printAllBtn.addActionListener(e -> {
            List<Student> all = manager.getAllStudents();
            if (all.isEmpty()) {
                outputArea.setText("No records to display.");
            } else {
                StringBuilder sb = new StringBuilder("All Student Records:\n\n");
                for (Student s : all) {
                    sb.append(s.getPerformanceSummary()).append("\n------------------\n");
                }
                outputArea.setText(sb.toString());
            }
        });

updateBtn.addActionListener(e -> {
    new UpdateStudentDialog(manager, outputArea);
});

        exportButton.addActionListener(e -> {
            String rollStr = JOptionPane.showInputDialog(null, "Enter Roll Number:");
            if (rollStr == null || rollStr.isEmpty()) return;

            try {
                int roll = Integer.parseInt(rollStr);
                Student s = manager.searchByRoll(roll);
                if (s == null) {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                    return;
                }

                String className = JOptionPane.showInputDialog(null, "Enter Class Name:");
                String examName = JOptionPane.showInputDialog(null, "Enter Exam Name:");
                if (className == null || examName == null || className.isEmpty() || examName.isEmpty()) return;

                manager.setClassName(className);
                manager.setExamName(examName);

                PreviewDialog preview = new PreviewDialog(null, s, className, examName);
                preview.setVisible(true);

                int confirm = JOptionPane.showConfirmDialog(null, "Export this marksheet to PDF?", "Confirm Export", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    manager.exportStudentToPDF(s, "Student_" + roll + "_marksheet.pdf");
                    JOptionPane.showMessageDialog(null, "Exported successfully.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });
	
	exportAllToSinglePDFBtn.addActionListener(e -> {
    List<Student> all = manager.getAllStudents();
    if (all.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No student records to export.");
        return;
    }

    String examName = JOptionPane.showInputDialog(null, "Enter Exam Name:");
    if (examName == null || examName.isEmpty()) return;

    manager.setExamName(examName);

    try {
        manager.exportAllStudentsToSinglePDF(all, "All_Students_Marksheet.pdf");
        JOptionPane.showMessageDialog(null, "All marksheets exported to a single PDF successfully.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Export failed: " + ex.getMessage());
    }

    ((MarksheetGUI) SwingUtilities.getWindowAncestor(this)).updateDashboard();
});	

    }
}