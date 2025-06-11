package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookListPanel extends JPanel {
    public BookListPanel(List<String[]> bookData) {
        setLayout(new BorderLayout());
        setOpaque(false);

        String[] columns = {"No", "Judul Buku", "Penulis", "Tahun", "Kategori"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        for (int i = 0; i < bookData.size(); i++) {
            String[] row = bookData.get(i);
            Object[] rowData = new Object[columns.length];
            rowData[0] = (i + 1) + ".";
            System.arraycopy(row, 0, rowData, 1, row.length);
            model.addRow(rowData);
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Poppins", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 15));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }
}
