import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    private JTextField userField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    private JButton loginButton = new JButton("Login");
    private JCheckBox rememberMe = new JCheckBox("Remember Me");

    public LoginScreen() {
    setTitle("Admin Login - Easwari Engineering College");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout()); // Set layout first

    // 🌟 Header Panel
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(0, 102, 204));
    JLabel headerLabel = new JLabel("EASWARI ENGINEERING COLLEGE", JLabel.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
    headerLabel.setForeground(Color.WHITE);
    headerPanel.add(headerLabel, BorderLayout.CENTER);

    try {
        ImageIcon logo = new ImageIcon("eec_logo.jpg");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        headerPanel.add(logoLabel, BorderLayout.SOUTH);
    } catch (Exception e) {
        // Logo not found, skip
    }

    // 🧩 Form Panel
    JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));
    formPanel.add(new JLabel("Username:"));
    formPanel.add(userField);
    formPanel.add(new JLabel("Password:"));
    formPanel.add(passField);
    formPanel.add(new JLabel(""));
    formPanel.add(rememberMe);

    // 🎯 Button Panel
    JPanel buttonPanel = new JPanel();
    loginButton.setBackground(new Color(0, 153, 76));
    loginButton.setForeground(Color.WHITE);
    loginButton.setFont(new Font("Arial", Font.BOLD, 14));
    loginButton.setFocusPainted(false);
    loginButton.setToolTipText("Login as admin");
    buttonPanel.add(loginButton);

    loginButton.addActionListener(e -> authenticate());
    getRootPane().setDefaultButton(loginButton);

    // 🧱 Add Panels
    add(headerPanel, BorderLayout.NORTH);
    add(formPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    // ✅ Final layout and visibility
    pack(); // AFTER adding components
    setMinimumSize(new Dimension(400, 350));
    setLocationRelativeTo(null);
    setVisible(true);
}
    private void authenticate() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if ((username.equals("admin") && password.equals("123")) ||
            (username.equals("Sam162007") && password.equals("123"))) {
            dispose();
            new MarksheetGUI();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        showSplashScreen();
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }

    // 🖼️ Splash Screen
    private static void showSplashScreen() {
    JWindow splash = new JWindow();

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(new Color(0, 102, 204));

    JLabel label = new JLabel("EASWARI ENGINEERING COLLEGE");
    label.setFont(new Font("SansSerif", Font.BOLD, 24));
    label.setForeground(Color.WHITE);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setVerticalAlignment(SwingConstants.CENTER);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;

    panel.add(label, gbc);
    splash.getContentPane().add(panel);

    splash.setSize(500, 200);
    splash.setLocationRelativeTo(null);
    splash.setVisible(true);

    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    splash.setVisible(false);
    splash.dispose();
}
}