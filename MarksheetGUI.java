import javax.swing.*;
import java.awt.*;

public class MarksheetGUI extends JFrame {
    private MarksheetManager manager = new MarksheetManager();
    private JTextArea outputArea = new JTextArea(10, 40);

    // 📊 Dashboard Labels (now instance variables)
    private JLabel totalLabel;
    private JLabel avgLabel;
    private JLabel topLabel;

    public MarksheetGUI() {
        setTitle("Student Marksheet Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // 🎨 Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        JLabel title = new JLabel("EASWARI ENGINEERING COLLEGE", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 📊 Dashboard Panel
        JPanel dashboardPanel = new JPanel(new GridLayout(3, 1));
        dashboardPanel.setBorder(BorderFactory.createTitledBorder("Dashboard Summary"));
        dashboardPanel.setBackground(new Color(240, 248, 255));

        totalLabel = new JLabel();
        avgLabel = new JLabel();
        topLabel = new JLabel();

        dashboardPanel.add(totalLabel);
        dashboardPanel.add(avgLabel);
        dashboardPanel.add(topLabel);
        mainPanel.add(dashboardPanel, BorderLayout.WEST);

        // 🖥️ Output Area
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setBackground(new Color(255, 255, 240));
        mainPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // 🧮 Button Panel
        ButtonPanel buttonPanel = new ButtonPanel(manager, outputArea);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 🔄 Initial Dashboard Update
        updateDashboard();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 🔄 Dashboard Refresh Method
    public void updateDashboard() {
        totalLabel.setText("Total Students: " + manager.getAllStudents().size());
        avgLabel.setText("Average %: " + String.format("%.2f", manager.getAveragePercentage()));
        Student top = manager.getTopScorer();
        topLabel.setText("Top Scorer: " + (top != null ? top.getName() : "N/A"));
    }
}