package Main;

import Auth.AuthSystem;

import javax.swing.*;

import Action.LibraryAction;
import Action.LibraryInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import ui.LoginFormPanel;
import ui.AestheticPanel;

public class LoginFrame extends JFrame {
    private AuthSystem auth;
    private LibraryInterface lib = LibraryAction.getInstance();

    public LoginFrame() {
        super("Login Perpustakaan");
        auth = new AuthSystem();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color cream = new Color(255, 251, 240);
        Color brown = new Color(120, 80, 40);
        Color accent = new Color(210, 180, 140);

        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(cream);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Logo
        JLabel logo = new JLabel("📚 Perpustakaan", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setFont(getFont());
                g2.setColor(new Color(210, 180, 140, 120));
                g2.drawString(getText(), 3, getHeight() - 13);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logo.setFont(new Font("Poppins", Font.BOLD, 36));
        logo.setForeground(brown);
        logo.setBorder(BorderFactory.createEmptyBorder(36, 0, 16, 0));
        mainPanel.add(logo, BorderLayout.NORTH);

        // Panel tengah (shadow)
        JPanel centerPanel = new AestheticPanel(
            new GridBagLayout(),
            null,
            32,
            8,
            new Color(120, 80, 40, 40)
        );
        centerPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Panel form login (rounded)
        JPanel roundedFormPanel = new AestheticPanel(
            new BorderLayout(),
            Color.WHITE,
            28,
            0,
            null
        );
        roundedFormPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

        // Form login
        LoginFormPanel formPanel = new LoginFormPanel(auth, lib, (library, user) -> {
            this.dispose();
            new MainMenuFrame(library, user);
        });
        formPanel.setOpaque(false);
        roundedFormPanel.add(formPanel, BorderLayout.CENTER);

        // Tombol Exit di bawah form login
        JButton exitButton = new JButton("Exit");
        exitButton.setFocusPainted(false);
        exitButton.setBackground(accent);
        exitButton.setForeground(brown);
        exitButton.setFont(new Font("Poppins", Font.BOLD, 14));
        exitButton.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        exitButton.addActionListener((ActionEvent e) -> System.exit(0));

        JPanel exitPanel = new JPanel(new BorderLayout());
        exitPanel.setOpaque(false);
        exitPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        exitPanel.add(exitButton, BorderLayout.CENTER);

        roundedFormPanel.add(exitPanel, BorderLayout.SOUTH);

        // Tambahkan ke centerPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(roundedFormPanel, gbc);

        // Footer
        JLabel footer = new JLabel("© 2025 Perpustakaan", SwingConstants.CENTER);
        footer.setFont(new Font("Poppins", Font.PLAIN, 13));
        footer.setForeground(accent);
        footer.setBorder(BorderFactory.createEmptyBorder(18, 0, 8, 0));
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setBackground(cream);

        // Fullscreen benar-benar penuh
        setUndecorated(true);
        setResizable(false);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        setBounds(0, 0, bounds.width, bounds.height);

        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}