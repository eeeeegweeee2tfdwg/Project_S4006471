package stage2_Cameron;

import java.util.ArrayList;
import java.util.List;

/**
 * A simulated database class that provides initial in-memory data for the
 * MelLibrary system.
 * This class does NOT connect to an actual database.
 */
public class db {

    /**
     * Provides an initial list of books. In a real application, this would fetch
     * from a database.
     * 
     * @return A List of pre-defined Book objects.
     */
    public static List<Book> getInitialBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("978-0321765723", "The Lord of the Rings", "J.R.R. Tolkien"));
        books.add(new Book("978-0743273565", "The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book("978-0439023528", "The Hunger Games", "Suzanne Collins")); // Added more instance
        books.add(new Book("978-0061120084", "To Kill a Mockingbird", "Harper Lee")); // Added more instance
        return books;
    }

    /**
     * Provides an initial list of members. In a real application, this would fetch
     * from a database.
     * 
     * @return A List of pre-defined Member objects.
     */
    public static List<Member> getInitialMembers() {
        List<Member> members = new ArrayList<>();
        members.add(new Member(101, "Alice Smith", "alice.smith@example.com"));
        members.add(new Member(102, "Bob Johnson", "0412 345 678"));
        members.add(new Member(103, "Charlie Brown", "charlie.b@example.com")); // Added more instance
        members.add(new Member(104, "Diana Prince", "0498 765 432")); // Added more instance
        return members;
    }
}