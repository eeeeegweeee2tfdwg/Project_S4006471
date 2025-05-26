package stage2_Cameron;

import java.util.List;

/**
 * An abstract class defining the common operations for a MelLibrary system.
 * This acts as the parent class for specific library implementations.
 */
public abstract class AbstractMelLibrary {

    /**
     * Adds a new book to the library system.
     * 
     * @param book The Book object to be added.
     */
    public abstract void addBook(Book book);

    /**
     * Searches for books based on a given query (e.g., ISBN, title, author).
     * 
     * @param query The search term.
     * @return A list of Book objects that match the query.
     */
    public abstract List<Book> searchBook(String query);

    /**
     * Removes a book from the library system using its ISBN.
     * 
     * @param isbn The ISBN of the book to be removed.
     */
    public abstract void removeBook(String isbn);

    /**
     * Adds a new member to the library system.
     * 
     * @param member The Member object to be added.
     */
    public abstract void addMember(Member member);

    /**
     * Searches for members based on a given query (e.g., ID, name, contact).
     * 
     * @param query The search term.
     * @return A list of Member objects that match the query.
     */
    public abstract List<Member> searchMember(String query);

    /**
     * Removes a member from the library system using their ID.
     * 
     * @param memberId The ID of the member to be removed.
     */
    public abstract void removeMember(int memberId);

    /**
     * Records a book loan for a specific member.
     * 
     * @param memberId The ID of the member loaning the book.
     * @param isbn     The ISBN of the book being loaned.
     * @param dueDate  The due date for the loan (e.g., "YYYY-MM-DD").
     */
    public abstract void loanBook(int memberId, String isbn, String dueDate);

    /**
     * Records the return of a book.
     * 
     * @param isbn The ISBN of the book being returned.
     */
    public abstract void returnBook(String isbn);

    /**
     * Retrieves a list of all books currently in the library system.
     * 
     * @return A list of all Book objects.
     */
    public abstract List<Book> listAllBooks();

    /**
     * Retrieves a list of all members currently registered in the library system.
     * 
     * @return A list of all Member objects.
     */
    public abstract List<Member> listAllMembers();

    /**
     * Retrieves a list of all loans associated with a specific member.
     * 
     * @param memberId The ID of the member whose loans are to be listed.
     * @return A list of Loan objects for the specified member.
     */
    public abstract List<Loan> listMemberLoans(int memberId);

    /**
     * Helper method to get a Book object by its ISBN.
     * 
     * @param isbn The ISBN of the book to retrieve.
     * @return The Book object if found, otherwise null.
     */
    public abstract Book getBookByIsbn(String isbn);

    /**
     * Helper method to get a Member object by its ID.
     * 
     * @param memberId The ID of the member to retrieve.
     * @return The Member object if found, otherwise null.
     */
    public abstract Member getMemberById(int memberId);
}