package com.library.Model;


import java.time.LocalDate;

public class Fines implements CsvMapper<Fines> {
    private String fineId;
    private String borrowId;
    private String studentId;
    private double amount;
    private String status;
    private LocalDate fineDate;

    public Fines(String fineId, String borrowId, String studentId, double amount, String status, LocalDate fineDate) {
        this.fineId = fineId;
        this.borrowId = borrowId;
        this.studentId = studentId;
        this.amount = amount;
        this.status = status;
        this.fineDate = fineDate;
    }

    // Getters
    public String getFineId() { return fineId; }
    public String getBorrowId() { return borrowId; }
    public String getStudentId() { return studentId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDate getFineDate() { return fineDate; }

    // Setters
    public void setFineId(String fineId) { this.fineId = fineId; }
    public void setBorrowId(String borrowId) { this.borrowId = borrowId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setStatus(String status) { this.status = status; }
    public void setFineDate(LocalDate fineDate) { this.fineDate = fineDate; }

    public void saveChanges() {
        try (Connections conn = new Connections()) {
            conn.updateFine(this);
        } catch (Exception e) {
            System.err.println("Error saving fine: " + e.getMessage());
        }
    }

    public void displayInfo() {
        System.out.println("\nFine Information");
        System.out.println("----------------");
        System.out.println("Fine ID    : " + fineId);
        System.out.println("Borrow ID  : " + borrowId);
        System.out.println("Student ID : " + studentId);
        System.out.println("Amount     : " + amount);
        System.out.println("Status     : " + status);
        System.out.println("Fine Date  : " + fineDate);
    }

    @Override
    public Fines mapFromCsv(String[] row) {
        if (row.length >= 6) {
            return new Fines(
                    row[0],  // fineId
                    row[1],  // borrowId
                    row[2],  // studentId
                    Double.parseDouble(row[3]),  // amount
                    row[4],  // status
                    LocalDate.parse(row[5])  // fineDate
            );
        }
        return null;
    }
}

