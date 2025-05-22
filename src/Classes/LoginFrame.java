package Classes;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    JTextField usernameField;
    JTextField codeField;

    public LoginFrame() {
        setTitle("Pong Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 20, 80, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 20, 180, 30);
        add(usernameField);

        JLabel codeLabel = new JLabel("Player Code:");
        codeLabel.setBounds(30, 60, 80, 30);
        add(codeLabel);

        codeField = new JTextField();
        codeField.setBounds(120, 60, 180, 30);
        add(codeField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 110, 120, 30);
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String code = codeField.getText().trim();

            if (username.isEmpty() || code.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and code.");
                return;
            }

            boolean success = PlayerDAO.loginOrRegister(username, code);
            if (success) {
                PongGame.loggedInUsername = username;
                new GameFrame();
                dispose(); // close login window
            } else {
                JOptionPane.showMessageDialog(this, "Login failed.");
            }
        });

        setVisible(true);
    }
}
