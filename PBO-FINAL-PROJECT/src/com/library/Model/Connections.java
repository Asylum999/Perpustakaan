package com.library.Model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Connections implements AutoCloseable{
    private final String folderPath = "data";

    private List<User> users;
    private volatile
    boolean isClosed = false;

    public Connections() {
        this.users = new ArrayList<>();
        loadUsers();
    }
    private void loadUsers() {
        try {
            List<User> loadedUsers = new ArrayList<>();
            loadedUsers.addAll(getAllStudents());
            loadedUsers.addAll(getAllAdmins());
            this.users = loadedUsers;
        } catch (Exception e) {
            System.err.println("Error loading users: " + e.getMessage());
            this.users = new ArrayList<>();
        }
    }



    // Generic CSV read method
    public <T> List<T> readCsvFile(String fileName, CsvMapper<T> mapper) {
        List<T> items = new ArrayList<>();
        String filePath = folderPath + File.separator + fileName;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] row = line.split(",");
                T item = mapper.mapFromCsv(row);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + filePath);
            e.printStackTrace();
        }

        return items;
    }

    // User-related methods
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.addAll(getAllStudents());
        users.addAll(getAllAdmins());
        return users;
    }

    public List<Student> getAllStudents() {
        return readCsvFile("User.csv", row -> {
            if (row.length >= 6 && "student".equalsIgnoreCase(row[0])) {
                return new Student(row[1], row[2], row[3], row[4], row[5]);
            }
            return null;
        });
    }

    public List<Admin> getAllAdmins() {
        return readCsvFile("User.csv", row -> {
            if (row.length >= 3 && "admin".equalsIgnoreCase(row[0])) {
                return new Admin(row[1], row[2]);
            }
            return null;
        });
    }

    public List<Borrowing> getAllBorrowings() {
        return readCsvFile("Borrowing.csv", row -> {
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
        });
    }


    // Book-related methods
    public List<Book> getAllBooks() {
        return readCsvFile("Books.csv", row -> {
            if (row.length >= 4) {
                return new Book(row[0], row[1], row[2], row[3]);
            }
            return null;
        });
    }

    public List<Fines> getAllFines() {
        return readCsvFile("Fines.csv", row -> {
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
        });
    }

    public List<Pengembalian> getAllPengembalian() {
        return readCsvFile("Pengembalian.csv", row -> {
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
        });
    }



    // Find methods
    public User findUserById(String id) {
        User student = findStudentById(id);
        if (student != null) {
            return student;
        }
        return findAdminById(id);
    }

    public Student findStudentByEmail(String email) {
        return getAllStudents().stream()
                .filter(student -> student.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }


    public Student findStudentById(String id) {
        return getAllStudents().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Admin findAdminById(String id) {
        return getAllAdmins().stream()
                .filter(admin -> admin.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Book findBookByIsbn(String isbn) {
        return getAllBooks().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    // Find methods for new entities
    public Borrowing findBorrowingById(String borrowId) {
        return getAllBorrowings().stream()
                .filter(borrowing -> borrowing.getBorrowId().equals(borrowId))
                .findFirst()
                .orElse(null);
    }

    public Fines findFineById(String fineId) {
        return getAllFines().stream()
                .filter(fine -> fine.getFineId().equals(fineId))
                .findFirst()
                .orElse(null);
    }

    public Pengembalian findPengembalianById(String returnId) {
        return getAllPengembalian().stream()
                .filter(returnItem -> returnItem.getReturnId().equals(returnId))
                .findFirst()
                .orElse(null);
    }

// Search methods
    public List<Student> searchStudents(String query) {
        String searchQuery = query.toLowerCase();
        return getAllStudents().stream()
                .filter(student ->
                        student.getUsername().toLowerCase().contains(searchQuery) ||
                                student.getId().toLowerCase().contains(searchQuery) ||
                                student.getFaculty().toLowerCase().contains(searchQuery) ||
                                student.getMajor().toLowerCase().contains(searchQuery) ||
                                student.getEmail().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());
    }

    public List<Book> searchBooks(String query) {
        String searchQuery = query.toLowerCase();
        return getAllBooks().stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(searchQuery) ||
                                book.getAuthor().toLowerCase().contains(searchQuery) ||
                                book.getCategory().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());
    }



    // Write methods
    public void writeFinesToCsv(List<Fines> fines) {
        String filePath = folderPath + File.separator + "Fines.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("FineID,BorrowID,StudentID,Amount,Status,FineDate\n");

            for (Fines fine : fines) {
                writer.write(String.format("%s,%s,%s,%.2f,%s,%s\n",
                        fine.getFineId(),
                        fine.getBorrowId(),
                        fine.getStudentId(),
                        fine.getAmount(),
                        fine.getStatus(),
                        fine.getFineDate()
                ));
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    public void writeBorrowingsToCsv(List<Borrowing> borrowings) {
        String filePath = folderPath + File.separator + "Borrowing.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("BorrowID,StudentID,ISBN,BorrowDate,DueDate,Status\n");

            for (Borrowing borrowing : borrowings) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                        borrowing.getBorrowId(),
                        borrowing.getStudentId(),
                        borrowing.getIsbn(),
                        borrowing.getBorrowDate(),
                        borrowing.getDueDate(),
                        borrowing.getStatus()
                ));
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    public void writePengembalianToCsv(List<Pengembalian> returns) {
        String filePath = folderPath + File.separator + "Pengembalian.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ReturnID,BorrowID,ReturnDate,Condition,Status\n");

            for (Pengembalian returnItem : returns) {
                writer.write(String.format("%s,%s,%s,%s,%s\n",
                        returnItem.getReturnId(),
                        returnItem.getBorrowId(),
                        returnItem.getReturnDate(),
                        returnItem.getCondition(),
                        returnItem.getStatus()
                ));
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }


    public void writeUsersToCsv(List<User> users) {
        String filePath = folderPath + File.separator + "User.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Type,Username,ID,Faculty,Major,Email\n");

            for (User user : users) {
                if (user instanceof Student) {
                    Student student = (Student) user;
                    writer.write(String.format("student,%s,%s,%s,%s,%s\n",
                            student.getUsername(),
                            student.getId(),
                            student.getFaculty(),
                            student.getMajor(),
                            student.getEmail()
                    ));
                } else if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    writer.write(String.format("admin,%s,%s,,,,\n",
                            admin.getUsername(),
                            admin.getId()
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    public void writeBooksToCsv(List<Book> books) {
        String filePath = folderPath + File.separator + "Books.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ISBN,Title,Author,Category\n");

            for (Book book : books) {
                writer.write(String.format("%s,%s,%s,%s\n",
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getCategory()
                ));
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    // CRUD operations
    public void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        writeUsersToCsv(users);
    }

    public void updateUser(User user) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                break;
            }
        }
        writeUsersToCsv(users);
    }

    public void addBorrowing(Borrowing borrowing) {
        List<Borrowing> borrowings = getAllBorrowings();
        borrowings.add(borrowing);
        writeBorrowingsToCsv(borrowings);
    }

    public void updateBorrowing(Borrowing borrowing) {
        List<Borrowing> borrowings = getAllBorrowings();
        for (int i = 0; i < borrowings.size(); i++) {
            if (borrowings.get(i).getBorrowId().equals(borrowing.getBorrowId())) {
                borrowings.set(i, borrowing);
                break;
            }
        }
        writeBorrowingsToCsv(borrowings);
    }

    public void deleteBorrowing(String borrowId) {
        List<Borrowing> borrowings = getAllBorrowings();
        borrowings.removeIf(borrowing -> borrowing.getBorrowId().equals(borrowId));
        writeBorrowingsToCsv(borrowings);
    }

    public void addFine(Fines fine) {
        List<Fines> fines = getAllFines();
        fines.add(fine);
        writeFinesToCsv(fines);
    }

    public void updateFine(Fines fine) {
        List<Fines> fines = getAllFines();
        for (int i = 0; i < fines.size(); i++) {
            if (fines.get(i).getFineId().equals(fine.getFineId())) {
                fines.set(i, fine);
                break;
            }
        }
        writeFinesToCsv(fines);
    }

    public void deleteFine(String fineId) {
        List<Fines> fines = getAllFines();
        fines.removeIf(fine -> fine.getFineId().equals(fineId));
        writeFinesToCsv(fines);
    }

    public void addPengembalian(Pengembalian pengembalian) {
        List<Pengembalian> returns = getAllPengembalian();
        returns.add(pengembalian);
        writePengembalianToCsv(returns);
    }

    public void updatePengembalian(Pengembalian pengembalian) {
        List<Pengembalian> returns = getAllPengembalian();
        for (int i = 0; i < returns.size(); i++) {
            if (returns.get(i).getReturnId().equals(pengembalian.getReturnId())) {
                returns.set(i, pengembalian);
                break;
            }
        }
        writePengembalianToCsv(returns);
    }

    public void deletePengembalian(String returnId) {
        List<Pengembalian> returns = getAllPengembalian();
        returns.removeIf(returnItem -> returnItem.getReturnId().equals(returnId));
        writePengembalianToCsv(returns);
    }


    public void deleteUser(String id) {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId().equals(id));
        writeUsersToCsv(users);
    }

    public void addBook(Book book) {
        List<Book> books = getAllBooks();
        books.add(book);
        writeBooksToCsv(books);
    }

    public void updateBook(Book book) {
        List<Book> books = getAllBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(book.getIsbn())) {
                books.set(i, book);
                break;
            }
        }
        writeBooksToCsv(books);
    }

    public void deleteBook(String isbn) {
        List<Book> books = getAllBooks();
        books.removeIf(book -> book.getIsbn().equals(isbn));
        writeBooksToCsv(books);
    }


    // Authentication method
    public User verifyLogin(String id, String username) {
        User user = findUserById(id);
        if (user != null && user.getUsername().equals(username)) {
            return user;
        }
        return null;
    }

    public void updateStudentPassword(String studentId, String newPassword) {
        List<User> users = getAllUsers();
        boolean found = false;

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user instanceof Student && user.getId().equals(studentId)) {
                // Update the password (ID in this case since we're using ID as password)
                users.set(i, new Student(
                        user.getUsername(),
                        newPassword,  // New password becomes the new ID
                        ((Student) user).getFaculty(),
                        ((Student) user).getMajor(),
                        ((Student) user).getEmail()
                ));
                found = true;
                break;
            }
        }

        if (found) {
            writeUsersToCsv(users);
        }
    }


@Override
    public void close() {
        if (!isClosed) {
            synchronized (this) {
                if (!isClosed) {
                    try {
                        if (users != null && !users.isEmpty()) {
                            writeUsersToCsv(new ArrayList<>(users));
                        }
                        users = null;
                        isClosed = true;
                    } catch (Exception e) {
                        System.err.println("Error closing connections: " + e.getMessage());
                    }
                }
            }
        }
    }

}