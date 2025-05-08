////Action
package Action;

import java.util.*;

public class LibraryAction implements LibraryInterface {
    private Map<String, BookBase> books = new HashMap<>();
    private List<BorrowRecord> history = new ArrayList<>();

    public LibraryAction() {
        books.put("goodnight pun-pun", new BookBase("Goodnight Pun-Pun", "Inio Asano", 2));
        books.put("Scheduled suicide day", new BookBase("Scheduled Suicide Day", "Rikako Akiyoshi", 1));
        books.put("happiness", new BookBase("Happiness", "Shuzo Oshimi", 3));
        books.put("blood on tracks", new BookBase("Blood on the Tracks", "Oshimi Shuuzou", 2));
        books.put("20th century boys", new BookBase("20th Century Boys", "Urasawa Naoki", 4));
    }

    private String normalizeTitle(String title) {
        return title.trim().toLowerCase();
    }

    @Override
    public void displayBooks() {
        System.out.println("Daftar Buku:");
        for (BookBase book : books.values()) {
            System.out.println("- " + book.getTitle() + " oleh " + book.getAuthor() + " (Stok: " + book.getStock() + ")");
        }
    }

    @Override
    public void borrowBook(String title, String user) {
        String key = normalizeTitle(title);
        BookBase book = books.get(key);
        if (book == null) {
            System.out.println("Buku '" + title + "' tidak ditemukan dalam sistem.");
            return;
        }
        if (book.getStock() == 0) {
            System.out.println("Buku '" + book.getTitle() + "' sedang tidak tersedia. Hubungi admin untuk menambah stok.");
            return;
        }
        book.borrow();
        history.add(new BorrowRecord(book.getTitle(), user));
        System.out.println("Berhasil meminjam: " + book.getTitle());
    }

    @Override
    public void returnBook(String title, String user) {
        String key = normalizeTitle(title);
        BookBase book = books.get(key);
        if (book == null) {
            System.out.println("Buku '" + title + "' tidak dikenal oleh sistem.");
            return;
        }
        book.returnBook();
        for (BorrowRecord r : history) {
            if (r.title.equalsIgnoreCase(book.getTitle()) && r.user.equals(user) && r.returnDate == null) {
                r.setReturnDate();
                break;
            }
        }
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
        String key = normalizeTitle(title);
        if (books.containsKey(key)) {
            books.get(key).returnBook();
            System.out.println("Stok buku '" + title + "' bertambah.");
        } else {
            books.put(key, new BookBase(title, author, 1));
            System.out.println("Buku baru ditambahkan: " + title);
        }
    }
}
