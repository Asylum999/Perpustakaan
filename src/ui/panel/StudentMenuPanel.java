package ui.panel;

import Action.LibraryInterface;
import Auth.User;
import ui.util.FacultyDialog;

import javax.swing.*;
import java.awt.*;

public class StudentMenuPanel extends JPanel {
    private final LibraryInterface lib;
    private final User user;
    private final JTextArea bookArea;
    private String userFaculty = "Tidak Diketahui";

    public StudentMenuPanel(LibraryInterface lib, User user) {
        this.lib = lib;
        this.user = user;

        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(255, 251, 240));
        Color brown = new Color(120, 80, 40);
        Color brownSoft = new Color(210, 180, 140);
        Color accent = new Color(255, 222, 173);
        Color fontDark = new Color(40, 30, 20);

        JLabel welcome = new JLabel("Selamat datang, " + user.getName(), SwingConstants.CENTER);
        welcome.setFont(new Font("Poppins", Font.BOLD, 34));
        welcome.setForeground(brown);
        welcome.setBorder(BorderFactory.createEmptyBorder(36, 0, 16, 0));
        add(welcome, BorderLayout.NORTH);

        bookArea = new JTextArea();
        bookArea.setEditable(false);
        bookArea.setFont(new Font("Poppins", Font.PLAIN, 18));
        bookArea.setBackground(accent);
        bookArea.setForeground(fontDark);
        bookArea.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        bookArea.setLineWrap(true);
        bookArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(bookArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(brownSoft, 2, true),
                "Daftar Buku",
                0, 2,
                new Font("Poppins", Font.BOLD, 17), brown
        ));
        scrollPane.setBackground(getBackground());
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, 350));

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));
        btnPanel.add(Box.createHorizontalGlue());

        JButton btnView = createModernButton("Lihat Buku", brownSoft, fontDark);
        btnPanel.add(btnView);
        btnPanel.add(Box.createHorizontalStrut(16));
        btnView.addActionListener(_ -> showBooks());

        // Gunakan dialog util baru
        userFaculty = FacultyDialog.showFacultySelectionDialog(this, brown, accent, fontDark);
        if (userFaculty == null) userFaculty = "Tidak Diketahui";

        JButton btnBorrow = createModernButton("Pinjam Buku", new Color(200, 180, 120), fontDark);
        btnPanel.add(btnBorrow);
        btnPanel.add(Box.createHorizontalStrut(16));
        btnBorrow.addActionListener(_ -> borrowBookDialog());

        JButton btnReturn = createModernButton("Kembalikan Buku", new Color(255, 200, 120), fontDark);
        btnPanel.add(btnReturn);
        btnPanel.add(Box.createHorizontalStrut(16));
        btnReturn.addActionListener(_ -> returnBookDialog());

        JButton btnStat = createModernButton("Statistik Fakultas", new Color(255, 220, 120), fontDark);
        btnPanel.add(btnStat);
        btnPanel.add(Box.createHorizontalStrut(16));
        btnStat.addActionListener(_ -> showFacultyStats());

        JButton btnLogout = createModernButton("Logout", new Color(220, 53, 69), Color.WHITE);
        btnLogout.setMaximumSize(new Dimension(200, 54));
        btnPanel.add(btnLogout);
        btnPanel.add(Box.createHorizontalGlue());
        btnLogout.addActionListener(_ -> {
            Window w = SwingUtilities.getWindowAncestor(this);
            w.dispose();
            new Main.LoginFrame();
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        centerPanel.add(btnPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scrollPane);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("© 2025 Perpustakaan", SwingConstants.CENTER);
        footer.setFont(new Font("Poppins", Font.BOLD, 16));
        footer.setForeground(brown);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(footer, BorderLayout.PAGE_END);
    }

    private JButton createModernButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, 18));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 2, true),
                BorderFactory.createEmptyBorder(12, 36, 12, 36)
        ));
        btn.setPreferredSize(new Dimension(200, 54));
        btn.setMaximumSize(new Dimension(200, 54));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    private void showBooks() {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);
        lib.displayBooks();
        System.out.flush();
        System.setOut(old);
        bookArea.setText(baos.toString());
    }

    private void showFacultyStats() {
        String report = "";
        try {
            java.lang.reflect.Method m = lib.getClass().getMethod("getFacultyRatioReport");
            report = (String) m.invoke(lib);
        } catch (Exception e) {
            report = "Statistik tidak tersedia.";
        }
        JOptionPane.showMessageDialog(this, report, "Statistik Fakultas", JOptionPane.INFORMATION_MESSAGE);
    }

    private void borrowBookDialog() {
        String id = JOptionPane.showInputDialog(this, "ID buku yang ingin dipinjam:");
        if (id != null && !id.trim().isEmpty()) {
            lib.borrowBook(id.trim(), user.getName(), userFaculty);
            showBooks();
        }
    }

    private void returnBookDialog() {
        String id = JOptionPane.showInputDialog(this, "ID buku yang ingin dikembalikan:");
        if (id != null && !id.trim().isEmpty()) {
            lib.returnBook(id.trim(), user.getName());
            showBooks();
        }
    }
}
