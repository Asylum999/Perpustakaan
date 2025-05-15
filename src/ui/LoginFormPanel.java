package ui;

import Auth.AuthSystem;
import Auth.User;
import Action.LibraryInterface;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class LoginFormPanel extends JPanel {
    private JComboBox<String> roleBox;
    private JTextField idField;
    private JTextField nameOrPassField;
    private JButton loginBtn;

    public LoginFormPanel(AuthSystem auth, LibraryInterface lib, BiConsumer<LibraryInterface, User> onLoginSuccess) {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(370, 260));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        Color brown = new Color(120, 80, 40);
        Color brownSoft = new Color(210, 180, 140);
        Color accent = new Color(255, 222, 173);
        Color fontDark = new Color(40, 30, 20);

        roleBox = new JComboBox<>(new String[]{"Admin", "Mahasiswa"});
        roleBox.setFont(new Font("Poppins", Font.PLAIN, 17));
        roleBox.setBackground(accent);
        roleBox.setForeground(fontDark);
        roleBox.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        add(styledField("Login sebagai", roleBox, brown, fontDark));
        add(Box.createVerticalStrut(16));

        idField = new JTextField();
        idField.setFont(new Font("Poppins", Font.PLAIN, 17));
        idField.setBackground(accent);
        idField.setForeground(fontDark);
        idField.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        add(styledField("Username/NIM", idField, brown, fontDark));
        add(Box.createVerticalStrut(16));

        nameOrPassField = new JTextField();
        nameOrPassField.setFont(new Font("Poppins", Font.PLAIN, 17));
        nameOrPassField.setBackground(accent);
        nameOrPassField.setForeground(fontDark);
        nameOrPassField.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        add(styledField("Password/Nama", nameOrPassField, brown, fontDark));
        add(Box.createVerticalStrut(24));

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
        add(loginBtn);

        loginBtn.addActionListener(_ -> doLogin(auth, lib, onLoginSuccess));
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

    private void doLogin(AuthSystem auth, LibraryInterface lib, BiConsumer<LibraryInterface, User> onLoginSuccess) {
        String role = (String) roleBox.getSelectedItem();
        String id = idField.getText().trim();
        String credential = nameOrPassField.getText().trim();
        boolean isAdmin = role.equals("Admin");

        User user = auth.login(id, credential, isAdmin);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Login gagal!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            onLoginSuccess.accept(lib, user);
        }
    }
}
