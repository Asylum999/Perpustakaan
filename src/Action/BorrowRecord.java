package Action;

import java.time.LocalDate;

public class BorrowRecord {
    public String title;
    public String user;
    public String faculty;
    public LocalDate borrowDate;
    public LocalDate returnDate;

    public BorrowRecord(String title, String user, String faculty) {
        this.title = title;
        this.user = user;
        this.faculty = faculty;
        this.borrowDate = LocalDate.now();
    }

    public void setReturnDate() {
        this.returnDate = LocalDate.now();
    }

    public String toString() {
        return user + " (" + faculty + ") meminjam '" + title + "' pada " + borrowDate +
                (returnDate != null ? ", dikembalikan pada " + returnDate : ", belum dikembalikan");
    }
}