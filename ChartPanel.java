import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ChartPanel extends JPanel {
    private Map<String, Integer> gradeData;

    public ChartPanel(Map<String, Integer> gradeData) {
        this.gradeData = gradeData;
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gradeData == null || gradeData.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();
        int barWidth = width / gradeData.size();
        int maxCount = gradeData.values().stream().max(Integer::compare).orElse(1);

        int i = 0;
        for (Map.Entry<String, Integer> entry : gradeData.entrySet()) {
            int barHeight = (int) ((entry.getValue() / (double) maxCount) * (height - 50));
            int x = i * barWidth + 20;
            int y = height - barHeight - 30;

            g.setColor(Color.BLUE);
            g.fillRect(x, y, barWidth - 40, barHeight);
            g.setColor(Color.BLACK);
            g.drawString(entry.getKey(), x + 5, height - 10);
            g.drawString(String.valueOf(entry.getValue()), x + 5, y - 5);
            i++;
        }
    }
}