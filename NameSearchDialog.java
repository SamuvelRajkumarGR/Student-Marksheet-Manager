import javax.swing.*;
import java.util.List;

public class NameSearchDialog {
    public NameSearchDialog(MarksheetManager manager, JTextArea outputArea) {
        String keyword = JOptionPane.showInputDialog("Enter name keyword:");
        List<Student> matches = manager.searchByName(keyword);
        if (matches.isEmpty()) {
            outputArea.setText("No matches found.");
        } else {
            StringBuilder sb = new StringBuilder("Matches:\n");
            for (Student s : matches) {
                sb.append("Roll No: ").append(s.getRollNo())
                  .append(", Name: ").append(s.getName())
                  .append(", Grade: ").append(s.getGrade()).append("\n");
            }
            outputArea.setText(sb.toString());
        }
    }
}