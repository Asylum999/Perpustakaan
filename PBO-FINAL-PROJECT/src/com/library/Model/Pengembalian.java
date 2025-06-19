package com.library.Model;

import java.time.LocalDate;

public class Pengembalian implements CsvMapper<Pengembalian> {
    private String returnId;
    private String borrowId;
    private LocalDate returnDate;
    private String condition;
    private String status;

    public Pengembalian(String returnId, String borrowId, LocalDate returnDate,
                        String condition, String status) {
        this.returnId = returnId;
        this.borrowId = borrowId;
        this.returnDate = returnDate;
        this.condition = condition;
        this.status = status;
    }

    // Getters
    public String getReturnId() { return returnId; }
    public String getBorrowId() { return borrowId; }
    public LocalDate getReturnDate() { return returnDate; }
    public String getCondition() { return condition; }
    public String getStatus() { return status; }

    // Setters
    public void setReturnId(String returnId) { this.returnId = returnId; }
    public void setBorrowId(String borrowId) { this.borrowId = borrowId; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setCondition(String condition) { this.condition = condition; }
    public void setStatus(String status) { this.status = status; }

    // Save changes to CSV
    public void saveChanges() {
        try (Connections conn = new Connections()) {
            conn.updatePengembalian(this);
        } catch (Exception e) {
            System.err.println("Error saving return: " + e.getMessage());
        }
    }

    // Display return information
    public void displayInfo() {
        System.out.println("\nReturn Information");
        System.out.println("-----------------");
        System.out.println("Return ID   : " + returnId);
        System.out.println("Borrow ID   : " + borrowId);
        System.out.println("Return Date : " + returnDate);
        System.out.println("Condition   : " + condition);
        System.out.println("Status      : " + status);
    }

    // Helper method to check if return is late
    public boolean isLate(Borrowing borrowing) {
        return returnDate.isAfter(borrowing.getDueDate());
    }

    @Override
    public String toString() {
        return String.format("Pengembalian{returnId='%s', borrowId='%s', returnDate='%s', condition='%s', status='%s'}",
                returnId, borrowId, returnDate, condition, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pengembalian that = (Pengembalian) obj;
        return returnId.equals(that.returnId);
    }

    @Override
    public int hashCode() {
        return returnId.hashCode();
    }

    @Override
    public Pengembalian mapFromCsv(String[] row) {
        if (row.length >= 5) {
            return new Pengembalian(
                    row[0],  // returnId
                    row[1],  // borrowId
                    LocalDate.parse(row[2]),  // returnDate
                    row[3],  // condition
                    row[4]   // status
            );
        }
        return null;
    }
}
