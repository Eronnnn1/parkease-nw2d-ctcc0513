import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Nice blue
    private static final Color SECONDARY_COLOR = new Color(236, 240, 241); // Light gray

    public LoginForm() {
        setTitle("Park-Ease");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Create logo/header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        // Add system name
        JLabel titleLabel = new JLabel("Park-Ease");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(PRIMARY_COLOR);
        
        // Add welcome message
        JLabel welcomeLabel = new JLabel("Parking Management System");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.GRAY);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.CENTER);
        headerPanel.add(welcomeLabel, BorderLayout.SOUTH);

        // Create form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Username field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField = new JTextField(20);
        styleTextField(usernameField);

        // Password field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        styleTextField(passwordField);

        // Login button
        loginButton = new JButton("Login");
        styleButton(loginButton);

        // Add components to form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 15, 0);
        formPanel.add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 0);
        formPanel.add(passLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 25, 0);
        formPanel.add(passwordField, gbc);

        gbc.gridy = 4;
        formPanel.add(loginButton, gbc);

        // Add hint panel
        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hintPanel.setBackground(Color.WHITE);
        JLabel hintLabel = new JLabel("Staff: staff/123 | Admin: admin/123");
        hintLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        hintLabel.setForeground(Color.GRAY);
        hintPanel.add(hintLabel);

        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(hintPanel, BorderLayout.SOUTH);

        // Add action listeners
        loginButton.addActionListener(e -> login());
        // Add enter key listener
        KeyListener enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        };
        usernameField.addKeyListener(enterListener);
        passwordField.addKeyListener(enterListener);

        add(mainPanel);
    }

    private void styleTextField(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 35));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        for (User user : Main.getUsers()) {
            if (user.authenticate(username, password)) {
                if (user.getRole().equals("STAFF")) {
                    new StaffDashboard().setVisible(true);
                } else {
                    new ManagerDashboard().setVisible(true);
                }
                this.dispose();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, 
            "Invalid username or password!", 
            "Login Error", 
            JOptionPane.ERROR_MESSAGE);
    }
} 