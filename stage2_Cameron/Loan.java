package stage2_Cameron;

public class Loan {
    private int loanId;
    private int memberId;
    private String isbn;
    private String dueDate;

    public Loan(int loanId, int memberId, String isbn, String dueDate) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.dueDate = dueDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}