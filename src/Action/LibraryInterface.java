////LibraryInterface
package Action;

public interface LibraryInterface {
    void displayBooks();
    void borrowBook(String title, String user);
    void returnBook(String title, String user);
    void viewStock();
    void viewHistory();
    void addBook(String title, String author);
}