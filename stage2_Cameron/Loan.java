package stage2_Cameron;

/**
 * Represents a book loan transaction in the MelLibrary system.
 */
public class Loan {
    private int loanId;
    private int memberId;
    private String isbn;
    private String dueDate; // Stored as a String for simplicity, e.g., "YYYY-MM-DD"

    /**
     * Default constructor for the Loan class.
     * Initializes attributes to default values.
     */
    public Loan() {
        this.loanId = 0;
        this.memberId = 0;
        this.isbn = "";
        this.dueDate = "";
    }

    /**
     * Parameterized constructor for the Loan class.
     * 
     * @param loanId   The unique ID of the loan.
     * @param memberId The ID of the member who took the loan.
     * @param isbn     The ISBN of the book that was loaned.
     * @param dueDate  The date the book is due, in "YYYY-MM-DD" format.
     */
    public Loan(int loanId, int memberId, String isbn, String dueDate) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.dueDate = dueDate;
    }

    /**
     * Gets the unique ID of the loan.
     * 
     * @return The loan ID.
     */
    public int getLoanId() {
        return loanId;
    }

    /**
     * Sets the unique ID of the loan.
     * 
     * @param loanId The new loan ID.
     */
    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    /**
     * Gets the ID of the member associated with this loan.
     * 
     * @return The member ID.
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * Sets the ID of the member associated with this loan.
     * 
     * @param memberId The new member ID.
     */
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    /**
     * Gets the ISBN of the book associated with this loan.
     * 
     * @return The ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book associated with this loan.
     * 
     * @param isbn The new ISBN.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the due date of the loan.
     * 
     * @return The due date string.
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the loan.
     * 
     * @param dueDate The new due date string.
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns a string representation of the Loan object.
     * 
     * @return A string containing the loan's ID, associated member ID, book ISBN,
     *         and due date.
     */
    @Override
    public String toString() {
        return "Loan ID: " + loanId + ", Member ID: " + memberId + ", ISBN: " + isbn + ", Due Date: " + dueDate;
    }
}