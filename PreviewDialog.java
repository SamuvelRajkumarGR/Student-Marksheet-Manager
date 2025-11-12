import javax.swing.*;
import java.awt.*;

public class PreviewDialog extends JDialog {
    public PreviewDialog(JFrame parent, Student student, String className, String examName) {
        super(parent, "Marksheet Preview", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);

        JTextArea previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append("EASWARI ENGINEERING COLLEGE\n");
        sb.append("Result Of: ").append(examName).append("\n");
        sb.append("Class: ").append(className).append("\n");
        sb.append("Roll No.: ").append(student.getRollNo()).append("\n");
        sb.append("Name: ").append(student.getName()).append("\n");
        sb.append("Date Added: ").append(student.getDateAdded()).append("\n\n");

        sb.append(String.format("%-10s %-15s %-15s %-10s %-10s\n", "Subject", "Total Marks", "Marks Obtained", "Grade", "Remarks"));
        int[] marks = student.getMarks();
        for (int i = 0; i < marks.length; i++) {
            sb.append(String.format("Sub-%d     100            %-15d %-10s %-10s\n", i + 1, marks[i], student.getGrade(), getRemark(marks[i])));
        }

        sb.append(String.format("\nTotal: %-3d / 500\n", student.getTotal()));
        sb.append("\n\nSignature: ____________________");

        previewArea.setText(sb.toString());
        add(new JScrollPane(previewArea));
    }

    private String getRemark(int mark) {
        if (mark >= 90) return "Excellent";
        else if (mark >= 80) return "Very Good";
        else if (mark >= 70) return "Good";
        else if (mark >= 60) return "Fair";
        else return "Needs Improvement";
    }
}