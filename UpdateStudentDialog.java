import javax.swing.*;
import java.awt.*;

public class UpdateStudentDialog {
    public UpdateStudentDialog(MarksheetManager manager, JTextArea outputArea) {
        String rollInput = JOptionPane.showInputDialog(null, "Enter Roll No to update:");
        if (rollInput == null || rollInput.isEmpty()) return;

        try {
            int roll = Integer.parseInt(rollInput);
            Student s = manager.searchByRoll(roll);
            if (s == null) {
                JOptionPane.showMessageDialog(null, "Student not found.");
                return;
            }

            JTextField newRollField = new JTextField(String.valueOf(s.getRollNo()));
            JTextField nameField = new JTextField(s.getName());
            JTextField[] markFields = new JTextField[5];
            int[] marks = s.getMarks();
            for (int i = 0; i < 5; i++) {
                markFields[i] = new JTextField(String.valueOf(marks[i]));
            }

            JPanel panel = new JPanel(new GridLayout(8, 2));
            panel.add(new JLabel("Current Roll No:")); panel.add(new JLabel(String.valueOf(s.getRollNo())));
            panel.add(new JLabel("New Roll No:")); panel.add(newRollField);
            panel.add(new JLabel("New Name:")); panel.add(nameField);
            for (int i = 0; i < 5; i++) {
                panel.add(new JLabel("New Mark " + (i + 1) + ":")); panel.add(markFields[i]);
            }

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Student", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                int newRoll = Integer.parseInt(newRollField.getText());
                String newName = nameField.getText();
                int[] newMarks = new int[5];
                for (int i = 0; i < 5; i++) {
                    newMarks[i] = Integer.parseInt(markFields[i].getText());
                }

                boolean success = manager.updateStudent(roll, newRoll, newName, newMarks);
                outputArea.setText(success ? "Student updated successfully." : "Update failed.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
        }
    }
}