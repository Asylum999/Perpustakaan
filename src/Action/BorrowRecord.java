///peminjaman
package Action;

import java.time.LocalDate;

public class BorrowRecord {
    public String title;
    public String user;
    public LocalDate borrowDate;
    public LocalDate returnDate;

    public BorrowRecord(String title, String user) {
        this.title = title;
        this.user = user;
        this.borrowDate = LocalDate.now();
    }

    public void setReturnDate() {
        this.returnDate = LocalDate.now();
    }

    public String toString() {
        return user + " meminjam '" + title + "' pada " + borrowDate +
                (returnDate != null ? ", dikembalikan pada " + returnDate : ", belum dikembalikan");
    }
}