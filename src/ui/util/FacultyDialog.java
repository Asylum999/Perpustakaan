package ui.util;

import javax.swing.*;
import java.awt.*;

public class FacultyDialog {
    public static String showFacultySelectionDialog(Component parent, Color labelColor, Color bgColor, Color fontColor) {
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
        okBtn.addActionListener(_ -> {
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
