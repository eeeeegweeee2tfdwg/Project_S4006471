package stage2_Cameron;

/**
 * Represents a book in the MelLibrary system.
 */
public class Book {
    private String isbn;
    private String title;
    private String author;

    /**
     * Default constructor for the Book class.
     * Initializes attributes to default empty values.
     */
    public Book() {
        this.isbn = "";
        this.title = "";
        this.author = "";
    }

    /**
     * Parameterized constructor for the Book class.
     * 
     * @param isbn   The International Standard Book Number of the book.
     * @param title  The title of the book.
     * @param author The author of the book.
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    /**
     * Gets the ISBN of the book.
     * 
     * @return The ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * 
     * @param isbn The new ISBN.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the title of the book.
     * 
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * 
     * @param title The new title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     * 
     * @return The author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * 
     * @param author The new author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns a string representation of the Book object.
     * 
     * @return A string containing the book's ISBN, title, and author.
     */
    @Override
    public String toString() {
        return "ISBN: " + isbn + ", Title: \"" + title + "\", Author: " + author;
    }
}