import javax.swing.*;
import java.util.Map;

public class ChartWindow extends JFrame {
    public ChartWindow(Map<String, Integer> gradeData) {
        setTitle("Grade Distribution Chart");
        add(new ChartPanel(gradeData));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}