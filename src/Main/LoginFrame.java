package Main;

import Auth.AuthSystem;
import Auth.User;

import javax.swing.*;

import Action.LibraryAction;
import Action.LibraryInterface;

import java.awt.*;

public class LoginFrame extends JFrame {
    private JComboBox<String> roleBox;
    private JTextField idField;
    private JTextField nameOrPassField;
    private JButton loginBtn;
    private AuthSystem auth;
    private LibraryInterface lib = LibraryAction.getInstance();

    public LoginFrame() {
        super("Login Perpustakaan");
        auth = new AuthSystem();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(440, 420));
        setSize(480, 480);
        setLocationRelativeTo(null);

        // Modern color palette
        Color cream = new Color(255, 251, 240);
        Color brown = new Color(120, 80, 40);
        Color brownSoft = new Color(210, 180, 140);
        Color accent = new Color(255, 222, 173);
        Color fontDark = new Color(40, 30, 20);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(cream);

        // Logo
        JLabel logo = new JLabel("📚 Perpustakaan", SwingConstants.CENTER);
        logo.setFont(new Font("Poppins", Font.BOLD, 32));
        logo.setForeground(brown);
        logo.setBorder(BorderFactory.createEmptyBorder(36, 0, 16, 0));
        mainPanel.add(logo, BorderLayout.NORTH);

        // Centered form panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setMaximumSize(new Dimension(370, 260));
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        roleBox = new JComboBox<>(new String[]{"Admin", "Mahasiswa"});
        roleBox.setFont(new Font("Poppins", Font.PLAIN, 17));
        roleBox.setBackground(accent);
        roleBox.setForeground(fontDark);
        roleBox.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        formPanel.add(styledField("Login sebagai", roleBox, brown, fontDark));
        formPanel.add(Box.createVerticalStrut(16));

        idField = new JTextField();
        idField.setFont(new Font("Poppins", Font.PLAIN, 17));
        idField.setBackground(accent);
        idField.setForeground(fontDark);
        idField.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        formPanel.add(styledField("Username/NIM", idField, brown, fontDark));
        formPanel.add(Box.createVerticalStrut(16));

        nameOrPassField = new JTextField();
        nameOrPassField.setFont(new Font("Poppins", Font.PLAIN, 17));
        nameOrPassField.setBackground(accent);
        nameOrPassField.setForeground(fontDark);
        nameOrPassField.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        formPanel.add(styledField("Password/Nama", nameOrPassField, brown, fontDark));
        formPanel.add(Box.createVerticalStrut(24));

        loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Poppins", Font.BOLD, 18));
        loginBtn.setBackground(brownSoft);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(brown, 2, true),
                BorderFactory.createEmptyBorder(12, 0, 12, 0)
        ));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        formPanel.add(loginBtn);

        // Center formPanel vertically and horizontally
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

        add(mainPanel);

        loginBtn.addActionListener(_ -> doLogin());

        setVisible(true);
    }

    private JPanel styledField(String label, JComponent field, Color labelColor, Color fontColor) {
        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(new Font("Poppins", Font.BOLD, 17));
        l.setForeground(labelColor);
        l.setPreferredSize(new Dimension(150, 38));
        field.setForeground(fontColor);
        p.add(l, BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private void doLogin() {
        String role = (String) roleBox.getSelectedItem();
        String id = idField.getText().trim();
        String credential = nameOrPassField.getText().trim();
        boolean isAdmin = role.equals("Admin");

        User user = auth.login(id, credential, isAdmin);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Login gagal!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            this.dispose();
            new MainMenuFrame(lib, user);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}