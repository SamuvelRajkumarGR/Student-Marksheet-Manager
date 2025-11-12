import javax.swing.*;
import java.awt.GridLayout;

public class AddStudentDialog {
    public AddStudentDialog(MarksheetManager manager, JTextArea outputArea) {
        JTextField rollField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField[] markFields = new JTextField[5];
        for (int i = 0; i < 5; i++) markFields[i] = new JTextField();

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Roll No:")); panel.add(rollField);
        panel.add(new JLabel("Name:")); panel.add(nameField);
        for (int i = 0; i < 5; i++) {
            panel.add(new JLabel("Mark " + (i + 1) + ":")); panel.add(markFields[i]);
        }

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int roll = Integer.parseInt(rollField.getText());
                String name = nameField.getText();
                int[] marks = new int[5];
                for (int i = 0; i < 5; i++) marks[i] = Integer.parseInt(markFields[i].getText());
                manager.addStudent(new Student(roll, name, marks));
                outputArea.setText("Student added successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
            }
        }
    }
}