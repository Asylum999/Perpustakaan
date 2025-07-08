package com.library.Model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Connections implements AutoCloseable {
    private final String folderPath = "data";
    private List<User> users;
    private volatile boolean isClosed = false;
    private final String CSV_DELIMITER = ";";

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

                String[] row = line.split(CSV_DELIMITER);
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

    public List<Pengembalian> getAllPengembalian() {
        return readCsvFile("Pengembalian.csv", row -> {
            if (row.length >= 5) {
                return new Pengembalian(
                        row[0],
                        row[1],
                        LocalDate.parse(row[2]),
                        row[3],
                        row[4]
                );
            }
            return null;
        });
    }

    public List<Fines> getAllFines() {
        return readCsvFile("Fines.csv", row -> {
            if (row.length >= 6) {
                return new Fines(
                        row[0],
                        row[1],
                        row[2],
                        Double.parseDouble(row[3]),
                        row[4],
                        LocalDate.parse(row[5])
                );
            }
            return null;
        });
    }

    public void writeUsersToCsv(List<User> users) {
        String filePath = folderPath + File.separator + "User.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Type;Username;ID;Faculty;Major;Email\n");

            for (User user : users) {
                if (user instanceof Student) {
                    Student student = (Student) user;
                    writer.write(String.format("student%s%s%s%s%s%s%s%s%s\n",
                            CSV_DELIMITER, student.getUsername(),
                            CSV_DELIMITER, student.getId(),
                            CSV_DELIMITER, student.getFaculty(),
                            CSV_DELIMITER, student.getMajor(),
                            CSV_DELIMITER + student.getEmail()));
                } else if (user instanceof Admin) {
                    Admin admin = (Admin) user;
                    writer.write(String.format("admin%s%s%s%s%s%s%s\n",
                            CSV_DELIMITER, admin.getUsername(),
                            CSV_DELIMITER, admin.getId(),
                            CSV_DELIMITER, CSV_DELIMITER, CSV_DELIMITER));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    public void writeFinesToCsv(List<Fines> fines) {
        String filePath = folderPath + File.separator + "Fines.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("FineID;BorrowID;StudentID;Amount;Status;FineDate\n");

            for (Fines fine : fines) {
                writer.write(String.format("%s%s%s%s%s%s%f%s%s%s%s\n",
                        fine.getFineId(), CSV_DELIMITER,
                        fine.getBorrowId(), CSV_DELIMITER,
                        fine.getStudentId(), CSV_DELIMITER,
                        fine.getAmount(), CSV_DELIMITER,
                        fine.getStatus(), CSV_DELIMITER,
                        fine.getFineDate()));
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    public void writePengembalianToCsv(List<Pengembalian> returns) {
        String filePath = folderPath + File.separator + "Pengembalian.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ReturnID;BorrowID;ReturnDate;Condition;Status\n");

            for (Pengembalian returnItem : returns) {
                writer.write(String.format("%s%s%s%s%s%s%s%s%s\n",
                        returnItem.getReturnId(), CSV_DELIMITER,
                        returnItem.getBorrowId(), CSV_DELIMITER,
                        returnItem.getReturnDate(), CSV_DELIMITER,
                        returnItem.getCondition(), CSV_DELIMITER,
                        returnItem.getStatus()));
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }

    public List<Borrowing> getAllBorrowings() {
        return readCsvFile("Borrowing.csv", row -> {
            if (row.length >= 6) {
                return new Borrowing(
                        row[0],
                        row[1],
                        row[2],
                        LocalDate.parse(row[3]),
                        LocalDate.parse(row[4]),
                        row[5]
                );
            }
            return null;
        });
    }

    public void writeBorrowingsToCsv(List<Borrowing> borrowings) {
        String filePath = folderPath + File.separator + "Borrowing.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("BorrowID;StudentID;ISBN;BorrowDate;DueDate;Status\n");
            for (Borrowing borrowing : borrowings) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s\n",
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

    public void addBook(Book book) {
        List<Book> books = getAllBooks();
        books.add(book);
        writeBooksToCsv(books);
    }

    public void updateBook(Book updatedBook) {
        List<Book> books = getAllBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(updatedBook.getIsbn())) {
                books.set(i, updatedBook);
                break;
            }
        }
        writeBooksToCsv(books);
    }

    // Hapus buku berdasarkan ISBN
    public void deleteBook(String isbn) {
        List<Book> books = getAllBooks();
        books.removeIf(book -> book.getIsbn().equals(isbn));
        writeBooksToCsv(books);
    }

    public List<Book> getAllBooks() {
        return readCsvFile("Book.csv", row -> {
            if (row.length >= 5) {
                return new Book(row[0], row[1], row[2], row[3], row[4]);
            }
            return null;
        });
    }


    public void writeBooksToCsv(List<Book> books) {
        String filePath = folderPath + File.separator + "Book.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ISBN;Title;Author;Category;Status\n");
            for (Book book : books) {
                writer.write(String.format("%s;%s;%s;%s;%s\n",
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getCategory(),
                        book.getStatus()
                ));
            }
        } catch (IOException e) {
            System.out.println("Failed to write to file: " + filePath);
            e.printStackTrace();
        }
    }


    public User findUserById(String id) {
        return getAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addUser(User user) {
        if (user == null || user.getId() == null || user.getUsername() == null) {
            System.err.println("Invalid user data. Skipping write.");
            return;
        }

        System.out.println(">>> [addUser] Adding user: " + user.getUsername() + ", ID: " + user.getId());

        this.users.add(user); // pakai list internal yang aktif
        writeUsersToCsv(this.users); // tulis ke file
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

    public User verifyLogin(String id, String username) {
        for (Admin admin : getAllAdmins()) {
            if (admin.getId().equals(id) && admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }

        for (Student student : getAllStudents()) {
            if (student.getId().equals(id) && student.getUsername().equalsIgnoreCase(username)) {
                return student;
            }
        }

        return null;
    }


    public Student findStudentByEmail(String email) {
        return getAllStudents().stream()
                .filter(student -> student.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public void updateStudentPassword(String studentId, String newPassword) {
        List<User> users = getAllUsers();
        boolean found = false;

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user instanceof Student && user.getId().equals(studentId)) {
                users.set(i, new Student(
                        user.getUsername(),
                        newPassword,
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

    public void addBorrowing(Borrowing borrowing) {
        String filePath = "data" + File.separator + "Borrowing.csv";
        File file = new File(filePath);
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (!fileExists || file.length() == 0) {
                writer.write("BorrowID;StudentID;ISBN;BorrowDate;DueDate;Status");
                writer.newLine();
            }

            writer.write(String.join(";", // pakai CSV_DELIMITER biar konsisten
                    borrowing.getBorrowId(),
                    borrowing.getStudentId(),
                    borrowing.getIsbn(),
                    borrowing.getBorrowDate().toString(),
                    borrowing.getDueDate().toString(),
                    borrowing.getStatus()
            ));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBookStatus(String isbn, String newStatus) {
        List<Book> books = getAllBooks();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setStatus(newStatus);
                break;
            }
        }
        writeBooksToCsv(books); // sudah aman dan konsisten ke folderPath
    }

    public Map<String, Integer> getMonthlyBorrowingStats() {
        Map<String, Integer> stats = new HashMap<>();
        List<Borrowing> borrowings = getAllBorrowings();

        for (Borrowing b : borrowings) {
            String month = b.getBorrowDate().getMonth().toString().substring(0, 3); // e.g. "JAN"
            month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase(); // format jadi "Jan"
            stats.put(month, stats.getOrDefault(month, 0) + 1);
        }

        return stats;
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

    // Keep other methods as-is or adapt similarly for delimiter if needed
}
