package Action;

public interface LibraryInterface {
    void displayBooks();
    void borrowBook(String id, String user);
    void borrowBook(String id, String user, String faculty);
    void returnBook(String id, String user);
    void viewStock();
    void viewHistory();
    void addBook(String title, String author);
}