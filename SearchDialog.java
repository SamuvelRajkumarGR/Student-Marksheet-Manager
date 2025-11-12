import javax.swing.*;

public class SearchDialog {
    public SearchDialog(MarksheetManager manager, JTextArea outputArea) {
        String input = JOptionPane.showInputDialog("Enter Roll No:");
        try {
            int roll = Integer.parseInt(input);
            Student s = manager.searchByRoll(roll);
            if (s != null) {
                outputArea.setText("Student Found:\n" + s.getPerformanceSummary());
            } else {
                outputArea.setText("No student found with roll no " + roll);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input.");
        }
    }
}