////Action
package Action;

import javax.swing.*;
import java.util.*;
import Action.stat.FacultyStats; // update import

public class LibraryAction implements LibraryInterface {
    private Map<String, BookBase> books = new LinkedHashMap<>();
    private List<BorrowRecord> history = new ArrayList<>();
    private FacultyStats facultyStats = new FacultyStats();

    // Tambahkan singleton instance
    private static LibraryAction instance;

    public static LibraryAction getInstance() {
        if (instance == null) {
            instance = new LibraryAction();
        }
        return instance;
    }

    private LibraryAction() {
        // id, title, author, stock, faculty
        books.put("B001", new BookBase("B001", "Matematika Diskrit", "Rinaldi Munir", 3, "Teknik Informatika"));
        books.put("B002", new BookBase("B002", "Algoritma dan Pemrograman", "Dony Ariyus", 2, "Teknik Informatika"));
        books.put("B003", new BookBase("B003", "Pengantar Ilmu Komunikasi", "Deddy Mulyana", 4, "Ilmu Komunikasi"));
        books.put("B004", new BookBase("B004", "Statistika Dasar", "Sudjana", 2, "Ilmu Komunikasi"));
        books.put("B005", new BookBase("B005", "Basis Data", "Fathansyah", 3, "Teknik Informatika"));
    }

    private BookBase getBookById(String id) {
        return books.get(id);
    }

    @Override
    public void displayBooks() {
        System.out.println("Daftar Buku:");
        for (BookBase book : books.values()) {
            System.out.println(book.getId() + " | " + book.getTitle() + " oleh " + book.getAuthor() +
                    " (Stok: " + book.getStock() + ") - Fakultas: " + book.getFaculty());
        }
    }

    @Override
    public void borrowBook(String id, String user, String faculty) {
        BookBase book = getBookById(id);
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Buku dengan ID '" + id + "' tidak ditemukan.", "Buku Tidak Ditemukan", JOptionPane.WARNING_MESSAGE);
            System.out.println("Buku dengan ID '" + id + "' tidak ditemukan.");
            return;
        }
        if (book.getStock() == 0) {
            JOptionPane.showMessageDialog(null, "Buku '" + book.getTitle() + "' sedang tidak tersedia.", "Stok Habis", JOptionPane.WARNING_MESSAGE);
            System.out.println("Buku '" + book.getTitle() + "' sedang tidak tersedia.");
            return;
        }
        book.borrow();
        history.add(new BorrowRecord(book.getTitle(), user, faculty));
        facultyStats.addBorrow(faculty);
        JOptionPane.showMessageDialog(null, "Buku '" + book.getTitle() + "' telah dipinjam.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Berhasil meminjam: " + book.getTitle());
    }

    // Overload untuk interface lama (tanpa faculty)
    @Override
    public void borrowBook(String id, String user) {
        borrowBook(id, user, "Tidak Diketahui");
    }

    @Override
    public void returnBook(String id, String user) {
        BookBase book = getBookById(id);
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Buku dengan ID '" + id + "' tidak dikenal oleh sistem.", "Buku Tidak Ditemukan", JOptionPane.WARNING_MESSAGE);
            System.out.println("Buku dengan ID '" + id + "' tidak dikenal oleh sistem.");
            return;
        }
        book.returnBook();
        for (BorrowRecord r : history) {
            if (r.title.equalsIgnoreCase(book.getTitle()) && r.user.equals(user) && r.returnDate == null) {
                r.setReturnDate();
                break;
            }
        }
        JOptionPane.showMessageDialog(null, "Buku '" + book.getTitle() + "' dikembalikan.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Buku dikembalikan: " + book.getTitle());
    }

    @Override
    public void viewStock() {
        displayBooks();
    }

    @Override
    public void viewHistory() {
        System.out.println("Riwayat Peminjaman:");
        for (BorrowRecord record : history) {
            System.out.println(record);
        }
    }

    @Override
    public void addBook(String title, String author) {
        // Untuk admin, tambahkan dialog input id dan fakultas
        String id = JOptionPane.showInputDialog(null, "Masukkan ID Buku:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID Buku tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String faculty = JOptionPane.showInputDialog(null, "Masukkan Fakultas Buku:");
        if (faculty == null || faculty.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fakultas tidak boleh kosong.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (books.containsKey(id)) {
            books.get(id).returnBook();
            JOptionPane.showMessageDialog(null, "Stok buku '" + title + "' bertambah.", "Info", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Stok buku '" + title + "' bertambah.");
        } else {
            books.put(id, new BookBase(id, title, author, 1, faculty));
            JOptionPane.showMessageDialog(null, "Buku baru ditambahkan: " + title, "Info", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Buku baru ditambahkan: " + title);
        }
    }

    // Fitur statistik fakultas
    public String getFacultyRatioReport() {
        return facultyStats.getReport();
    }
}
