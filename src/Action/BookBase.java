package Action;

public class BookBase {
    protected String id;
    protected String title;
    protected String author;
    protected int stock;
    protected String faculty;

    public BookBase(String id, String title, String author, int stock, String faculty) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.faculty = faculty;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }
    public String getFaculty() { return faculty; }

    public void borrow() { if (stock > 0) stock--; }
    public void returnBook() { stock++; }
}
