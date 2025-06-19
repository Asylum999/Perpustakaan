package com.library.Model;

import java.time.LocalDate;

public class Borrowing implements CsvMapper<Borrowing> {
    private String borrowId;
    private String studentId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String status;

    public Borrowing(String borrowId, String studentId, String isbn, LocalDate borrowDate, LocalDate dueDate, String status) {
        this.borrowId = borrowId;
        this.studentId = studentId;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters
    public String getBorrowId() { return borrowId; }
    public String getStudentId() { return studentId; }
    public String getIsbn() { return isbn; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    // Setters
    public void setBorrowId(String borrowId) { this.borrowId = borrowId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setStatus(String status) { this.status = status; }

    public void saveChanges() {
        try (Connections conn = new Connections()) {
            conn.updateBorrowing(this);
        } catch (Exception e) {
            System.err.println("Error saving borrowing: " + e.getMessage());
        }
    }

    public void displayInfo() {
        System.out.println("\nBorrowing Information");
        System.out.println("--------------------");
        System.out.println("Borrow ID  : " + borrowId);
        System.out.println("Student ID : " + studentId);
        System.out.println("ISBN       : " + isbn);
        System.out.println("Borrow Date: " + borrowDate);
        System.out.println("Due Date   : " + dueDate);
        System.out.println("Status     : " + status);
    }

    @Override
    public Borrowing mapFromCsv(String[] row) {
        if (row.length >= 6) {
            return new Borrowing(
                    row[0],  // borrowId
                    row[1],  // studentId
                    row[2],  // isbn
                    LocalDate.parse(row[3]),  // borrowDate
                    LocalDate.parse(row[4]),  // dueDate
                    row[5]   // status
            );
        }
        return null;
    }
}
