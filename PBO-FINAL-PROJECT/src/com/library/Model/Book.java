package com.library.Model;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;

    public Book(String isbn, String title, String author, String category) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.category = category;
    }

    // Getters
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Helper method for saving changes
    public void saveChanges() {
        Connections conn = new Connections();
        conn.updateBook(this);
    }

    // Helper method to delete this book
    public void delete() {
        Connections conn = new Connections();
        conn.deleteBook(this.isbn);
    }

    // Display book information
    public void displayInfo() {
        System.out.println("\nBook Information");
        System.out.println("---------------");
        System.out.println("ISBN     : " + isbn);
        System.out.println("Title    : " + title);
        System.out.println("Author   : " + author);
        System.out.println("Category : " + category);
    }

    // Override toString for better object representation
    @Override
    public String toString() {
        return String.format("Book{isbn='%s', title='%s', author='%s', category='%s'}",
                isbn, title, author, category);
    }

    // Override equals for proper object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    // Override hashCode for proper collection handling
    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
