package Main;

import Action.LibraryInterface;
import Auth.User;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private LibraryInterface lib;
    private User user;
    private JTextArea bookArea;
    private String userFaculty = "Tidak Diketahui";

    public MainMenuFrame(LibraryInterface lib, User user) {
        super("Menu Utama Perpustakaan");
        this.lib = lib;
        this.user = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(780, 580));
        setSize(820, 600);
        setLocationRelativeTo(null);

        // Modern color palette
        Color cream = new Color(255, 251, 240);
        Color brown = new Color(120, 80, 40);
        Color brownSoft = new Color(210, 180, 140);
        Color accent = new Color(255, 222, 173);
        Color fontDark = new Color(40, 30, 20);

        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        panel.setBackground(cream);

        JLabel welcome = new JLabel("Selamat datang, " + user.getName(), SwingConstants.CENTER);
        welcome.setFont(new Font("Poppins", Font.BOLD, 34));
        welcome.setForeground(brown);
        welcome.setBorder(BorderFactory.createEmptyBorder(36, 0, 16, 0));
        panel.add(welcome, BorderLayout.NORTH);

        // Area buku
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
        scrollPane.setBackground(cream);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        scrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, 350));

        // Panel tombol fitur (menggunakan BoxLayout agar tombol tidak ketutupan)
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));

        btnPanel.add(Box.createHorizontalGlue());

        JButton btnView = createModernButton("Lihat Buku", brownSoft, fontDark);
        btnPanel.add(btnView);
        btnPanel.add(Box.createHorizontalStrut(16));
        btnView.addActionListener(_ -> showBooks());

        if (user.isAdmin()) {
            JButton btnAdd = createModernButton("Tambah Buku", new Color(200, 180, 120), fontDark);
            btnPanel.add(btnAdd);
            btnPanel.add(Box.createHorizontalStrut(16));
            btnAdd.addActionListener(_ -> addBookDialog());

            JButton btnHistory = createModernButton("Riwayat", new Color(180, 180, 255), fontDark);
            btnPanel.add(btnHistory);
            btnPanel.add(Box.createHorizontalStrut(16));
            btnHistory.addActionListener(_ -> showHistory());

            JButton btnStat = createModernButton("Statistik Fakultas", new Color(255, 220, 120), fontDark);
            btnPanel.add(btnStat);
            btnPanel.add(Box.createHorizontalStrut(16));
            btnStat.addActionListener(_ -> showFacultyStats());
        } else {
            userFaculty = showFacultySelectionDialog(this, brown, accent, fontDark);
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
        }

        JButton btnLogout = createModernButton("Logout", new Color(220, 53, 69), Color.WHITE);
        btnLogout.setMaximumSize(new Dimension(200, 54));
        btnPanel.add(btnLogout);
        btnPanel.add(Box.createHorizontalGlue());
        btnLogout.addActionListener(_ -> {
            dispose();
            new LoginFrame();
        });

        // Gabungkan btnPanel dan scrollPane ke panel tengah (BoxLayout)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        centerPanel.add(btnPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(scrollPane);

        // Tambahkan filler agar tombol tidak ketutupan scrollpane saat resize
        centerPanel.add(Box.createVerticalGlue());

        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("© 2025 Perpustakaan", SwingConstants.CENTER);
        footer.setFont(new Font("Poppins", Font.BOLD, 16));
        footer.setForeground(brown);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(footer, BorderLayout.PAGE_END);

        add(panel);
        setVisible(true);
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

    private void showHistory() {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);
        lib.viewHistory();
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

    private void addBookDialog() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        Object[] msg = {
                "Judul:", titleField,
                "Penulis:", authorField
        };
        int opt = JOptionPane.showConfirmDialog(this, msg, "Tambah Buku", JOptionPane.OK_CANCEL_OPTION);
        if (opt == JOptionPane.OK_OPTION) {
            lib.addBook(titleField.getText(), authorField.getText());
            showBooks();
        }
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

    private String showFacultySelectionDialog(Component parent, Color labelColor, Color bgColor, Color fontColor) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(parent), "Pilih Fakultas", true);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(parent);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(labelColor, 2, true),
                BorderFactory.createEmptyBorder(36, 36, 36, 36)
        ));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Pilih Fakultas Anda");
        label.setFont(new Font("Poppins", Font.BOLD, 22));
        label.setForeground(labelColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        panel.add(Box.createVerticalStrut(28));

        String[] fakultas = {"Teknik Informatika", "Ilmu Komunikasi"};
        JComboBox<String> combo = new JComboBox<>(fakultas);
        combo.setFont(new Font("Poppins", Font.PLAIN, 18));
        combo.setBackground(Color.WHITE);
        combo.setForeground(fontColor);
        combo.setMaximumSize(new Dimension(260, 44));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(combo);

        panel.add(Box.createVerticalStrut(32));

        JButton okBtn = new JButton("Pilih");
        okBtn.setFont(new Font("Poppins", Font.BOLD, 17));
        okBtn.setBackground(labelColor);
        okBtn.setForeground(Color.WHITE);
        okBtn.setFocusPainted(false);
        okBtn.setBorder(BorderFactory.createEmptyBorder(12, 48, 12, 48));
        okBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        final String[] selected = {null};
        okBtn.addActionListener(e -> {
            selected[0] = (String) combo.getSelectedItem();
            dialog.dispose();
        });
        panel.add(okBtn);

        dialog.setContentPane(panel);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(labelColor, 2, true));
        dialog.setVisible(true);

        return selected[0];
    }
}