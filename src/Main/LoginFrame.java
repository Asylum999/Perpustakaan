package Main;

import Auth.AuthSystem;

import javax.swing.*;

import Action.LibraryAction;
import Action.LibraryInterface;

import java.awt.*;
import ui.LoginFormPanel;

public class LoginFrame extends JFrame {
    private AuthSystem auth;
    private LibraryInterface lib = LibraryAction.getInstance();

    public LoginFrame() {
        super("Login Perpustakaan");
        auth = new AuthSystem();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(440, 420));
        setSize(480, 480);
        setLocationRelativeTo(null);

        Color cream = new Color(255, 251, 240);
        Color brown = new Color(120, 80, 40);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(cream);

        JLabel logo = new JLabel("📚 Perpustakaan", SwingConstants.CENTER);
        logo.setFont(new Font("Poppins", Font.BOLD, 32));
        logo.setForeground(brown);
        logo.setBorder(BorderFactory.createEmptyBorder(36, 0, 16, 0));
        mainPanel.add(logo, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Gunakan LoginFormPanel
        LoginFormPanel formPanel = new LoginFormPanel(auth, lib, (library, user) -> {
            this.dispose();
            new MainMenuFrame(library, user);
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(formPanel, gbc);

        JLabel footer = new JLabel("© 2025 Perpustakaan", SwingConstants.CENTER);
        footer.setFont(new Font("Poppins", Font.BOLD, 15));
        footer.setForeground(brown);
        footer.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}