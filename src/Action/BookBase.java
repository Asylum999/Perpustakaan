////BookBase.java
package Action;

public class BookBase {
    protected String title;
    protected String author;
    protected int stock;

    public BookBase(String title, String author, int stock) {
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }

    public void borrow() { if (stock > 0) stock--; }
    public void returnBook() { stock++; }
}
