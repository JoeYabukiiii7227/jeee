import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class RegisterSystem extends JFrame {
    private JTextField usernameField;
    private JTextField phoneField;
    private static HashMap<String, String> userDatabase = new HashMap<>();

    public static boolean verifyUser(String username, String phone) {
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(phone);
    }

    public RegisterSystem() {
        setTitle("Para sa Kompyuter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color darkRed = new Color(109, 12, 23, 255);
                GradientPaint gradient = new GradientPaint(0, 0, darkRed, 0, getHeight(), new Color(80, 0, 0));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.YELLOW, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Logo
        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setOpaque(false);

        // Load and scale the image
        ImageIcon originalIcon = new ImageIcon("1.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JLabel logoLabel = new JLabel(scaledIcon);
        logoPanel.add(logoLabel);

        // Form components
        JLabel titleLabel = new JLabel("Create an Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Phone number field with country code and flag
        JPanel phonePanel = new JPanel();
        phonePanel.setOpaque(false);
        phonePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        // Add Philippine flag
        ImageIcon flagIcon = new ImageIcon("flag.png");
        Image flagImage = flagIcon.getImage().getScaledInstance(25, 15, Image.SCALE_SMOOTH);
        JLabel flagLabel = new JLabel(new ImageIcon(flagImage));
        
        JLabel countryCode = new JLabel("+63");
        countryCode.setForeground(Color.WHITE);
        phoneField = createStyledTextField();
        phoneField.setPreferredSize(new Dimension(180, 30));
        
        phonePanel.add(flagLabel);
        phonePanel.add(countryCode);
        phonePanel.add(phoneField);

        // Username field with placeholder
        usernameField = createStyledTextField();
        usernameField.putClientProperty("JTextField.placeholderText", "Username");
        
        // Password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.BLACK);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        passwordField.putClientProperty("JTextField.placeholderText", "Password");
        passwordField.setPreferredSize(new Dimension(180, 30));

        // Sign In button
        JButton signInButton = new JButton("Sign In");
        styleButton(signInButton);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(logoPanel, gbc);
        
        gbc.gridy = 1;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridy = 2;
        mainPanel.add(phonePanel, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(usernameField, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(signInButton, gbc);

        // Button action
        signInButton.addActionListener(e -> {
            if (validateRegistration()) {
                JOptionPane.showMessageDialog(this, 
                    "Registration Successful!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            }
        });

        add(mainPanel);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        return field;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(255, 215, 0));
        button.setForeground(new Color(139, 0, 0));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.YELLOW.darker(), 1));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 35));
    }

    private boolean validateRegistration() {
        String phone = phoneField.getText();
        String username = usernameField.getText();

        if (phone.isEmpty() || username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "All fields are required!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (userDatabase.containsKey(username)) {
            JOptionPane.showMessageDialog(this,
                "Username already exists!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        userDatabase.put(username, phone);
        return true;
    }

    private void clearFields() {
        phoneField.setText("");
        usernameField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterSystem registerSystem = new RegisterSystem();
            registerSystem.setVisible(true);
        });
    }
}