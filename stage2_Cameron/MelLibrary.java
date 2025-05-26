package stage2_Cameron;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet; // Using TreeSet to keep deleted IDs sorted for reuse

/**
 * Concrete implementation of the AbstractMelLibrary, managing library
 * operations
 * with in-memory data.
 */
public class MelLibrary extends AbstractMelLibrary {
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Book> books;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Member> members;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Loan> loans;
    private int nextMemberId;
    @SuppressWarnings("FieldMayBeFinal")
    private TreeSet<Integer> deletedMemberIds; // Stores deleted member IDs for reuse
    private int nextLoanId;

    /**
     * Default constructor for MelLibrary. Initializes all data structures
     * and loads initial data from the simulated 'db' class.
     */
    public MelLibrary() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.deletedMemberIds = new TreeSet<>();
        this.nextMemberId = 1; // Start member IDs from 1 or an appropriate base
        this.nextLoanId = 1; // Start loan IDs from 1 or an appropriate base
        initializeDataFromDb(); // Load initial data upon creation
    }

    /**
     * Constructor for MelLibrary that allows specifying initial IDs.
     * 
     * @param initialNextMemberId The starting ID for new members.
     * @param initialNextLoanId   The starting ID for new loans.
     */
    public MelLibrary(int initialNextMemberId, int initialNextLoanId) {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.deletedMemberIds = new TreeSet<>();
        this.nextMemberId = initialNextMemberId;
        this.nextLoanId = initialNextLoanId;
        initializeDataFromDb(); // Load initial data upon creation
    }

    /**
     * Initializes the library's data by loading books and members from the
     * simulated 'db' class. It also sets the next available member and loan IDs
     * based on the loaded data.
     */
    private void initializeDataFromDb() {
        // Load initial books
        List<Book> initialBooks = db.getInitialBooks();
        this.books.addAll(initialBooks);

        // Load initial members and find the highest ID to set nextMemberId
        List<Member> initialMembers = db.getInitialMembers();
        this.members.addAll(initialMembers);
        for (Member member : initialMembers) {
            if (member.getMemberId() >= this.nextMemberId) {
                this.nextMemberId = member.getMemberId() + 1;
            }
        }
        // Loans are typically not initialized from a static 'db' as they are dynamic
        // transactions
        // and are handled by the loanBook/returnBook methods.
    }

    /**
     * Adds a new book to the in-memory list.
     * 
     * @param book The Book object to be added.
     */
    @Override
    public void addBook(Book book) {
        this.books.add(book);
        System.out.println("Book added: " + book.getTitle()); // For console feedback
    }

    /**
     * Searches for books in the in-memory list based on ISBN, title, or author.
     * 
     * @param query The search term.
     * @return A list of matching Book objects.
     */
    @Override
    public List<Book> searchBook(String query) {
        List<Book> results = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();
        for (Book book : this.books) {
            if (book.getIsbn().toLowerCase().contains(lowerCaseQuery) ||
                    book.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                    book.getAuthor().toLowerCase().contains(lowerCaseQuery)) {
                results.add(book);
            }
        }
        return results;
    }

    /**
     * Removes a book from the in-memory list by its ISBN.
     * Note: Does not handle active loans for simplicity in this version.
     * 
     * @param isbn The ISBN of the book to be removed.
     */
    @Override
    public void removeBook(String isbn) {
        boolean removed = this.books.removeIf(book -> book.getIsbn().equals(isbn));
        if (removed) {
            System.out.println("Book with ISBN " + isbn + " removed.");
        } else {
            System.out.println("Book with ISBN " + isbn + " not found.");
        }
    }

    /**
     * Adds a new member to the in-memory list. Reuses deleted IDs if available.
     * 
     * @param member The Member object to be added.
     */
    @Override
    public void addMember(Member member) {
        if (member.getMemberId() == 0 && !deletedMemberIds.isEmpty()) {
            member.setMemberId(deletedMemberIds.pollFirst()); // Reuse smallest deleted ID
        } else if (member.getMemberId() == 0) {
            member.setMemberId(nextMemberId++); // Assign new sequential ID
        }
        this.members.add(member);
        System.out.println("Member added: " + member.getName() + " with ID: " + member.getMemberId());
    }

    /**
     * Searches for members in the in-memory list based on ID, name, or contact.
     * 
     * @param query The search term.
     * @return A list of matching Member objects.
     */
    @Override
    public List<Member> searchMember(String query) {
        List<Member> results = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();
        for (Member member : this.members) {
            if (String.valueOf(member.getMemberId()).contains(query) ||
                    member.getName().toLowerCase().contains(lowerCaseQuery) ||
                    member.getContact().toLowerCase().contains(lowerCaseQuery)) {
                results.add(member);
            }
        }
        return results;
    }

    /**
     * Removes a member from the in-memory list by their ID. The ID is stored for
     * reuse.
     * Note: Does not handle active loans for simplicity in this version.
     * 
     * @param memberId The ID of the member to be removed.
     */
    @Override
    public void removeMember(int memberId) {
        boolean removed = this.members.removeIf(member -> member.getMemberId() == memberId);
        if (removed) {
            this.deletedMemberIds.add(memberId); // Add ID to set for reuse
            System.out.println("Member with ID " + memberId + " removed.");
        } else {
            System.out.println("Member with ID " + memberId + " not found.");
        }
    }

    /**
     * Records a new loan if the member and book exist and the book is not already
     * loaned.
     * 
     * @param memberId The ID of the member.
     * @param isbn     The ISBN of the book.
     * @param dueDate  The due date string.
     */
    @Override
    public void loanBook(int memberId, String isbn, String dueDate) {
        Member member = getMemberById(memberId);
        Book book = getBookByIsbn(isbn);
        if (member != null && book != null) {
            if (!isBookLoaned(isbn)) {
                Loan newLoan = new Loan(nextLoanId++, memberId, isbn, dueDate);
                this.loans.add(newLoan);
                System.out.println("Book '" + book.getTitle() + "' loaned to " + member.getName());
            } else {
                System.out.println("Error: Book '" + book.getTitle() + "' is already on loan.");
            }
        } else {
            System.out.println("Error: Member (ID: " + memberId + ") or Book (ISBN: " + isbn + ") not found.");
        }
    }

    /**
     * Checks if a book is currently out on loan.
     * 
     * @param isbn The ISBN of the book to check.
     * @return true if the book is loaned, false otherwise.
     */
    private boolean isBookLoaned(String isbn) {
        for (Loan loan : this.loans) {
            if (loan.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Records the return of a book by removing its corresponding loan entry.
     * 
     * @param isbn The ISBN of the book being returned.
     */
    @Override
    public void returnBook(String isbn) {
        boolean removed = this.loans.removeIf(loan -> loan.getIsbn().equals(isbn));
        if (removed) {
            System.out.println("Book with ISBN " + isbn + " returned.");
        } else {
            System.out.println("No active loan found for book with ISBN " + isbn);
        }
    }

    /**
     * Returns a copy of the list of all books in the library.
     * 
     * @return A List of all Book objects.
     */
    @Override
    public List<Book> listAllBooks() {
        return new ArrayList<>(this.books); // Return a copy to prevent external modification
    }

    /**
     * Returns a copy of the list of all members in the library.
     * 
     * @return A List of all Member objects.
     */
    @Override
    public List<Member> listAllMembers() {
        return new ArrayList<>(this.members); // Return a copy
    }

    /**
     * Returns a list of all loans associated with a specific member.
     * 
     * @param memberId The ID of the member.
     * @return A list of Loan objects for the specified member.
     */
    @Override
    public List<Loan> listMemberLoans(int memberId) {
        List<Loan> memberLoans = new ArrayList<>();
        for (Loan loan : this.loans) {
            if (loan.getMemberId() == memberId) {
                memberLoans.add(loan);
            }
        }
        return memberLoans;
    }

    /**
     * Helper method to find a Book object by its ISBN.
     * 
     * @param isbn The ISBN to search for.
     * @return The Book object if found, otherwise null.
     */
    @Override
    public Book getBookByIsbn(String isbn) {
        for (Book book : this.books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Helper method to find a Member object by its ID.
     * 
     * @param memberId The ID to search for.
     * @return The Member object if found, otherwise null.
     */
    @Override
    public Member getMemberById(int memberId) {
        for (Member member : this.members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }

    /**
     * Helper method to get a Loan object by book ISBN.
     * 
     * @param isbn The ISBN of the book associated with the loan.
     * @return The Loan object if found, otherwise null.
     */
    public Loan getLoanByIsbn(String isbn) {
        for (Loan loan : this.loans) {
            if (loan.getIsbn().equals(isbn)) {
                return loan;
            }
        }
        return null;
    }
}