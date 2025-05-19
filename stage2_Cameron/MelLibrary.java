package stage2_Cameron;

import java.util.ArrayList;
import java.util.List;

public class MelLibrary extends AbstractMelLibrary {
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<Loan> loans;
    private int nextMemberId = 1;
    private int nextLoanId = 1;

    public MelLibrary() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
        initializeData(); // Add some initial data for testing
    }

    private void initializeData() {
        books.add(new Book("978-0321765723", "The Lord of the Rings", "J.R.R. Tolkien"));
        books.add(new Book("978-0743273565", "The Great Gatsby", "F. Scott Fitzgerald"));
        members.add(new Member(nextMemberId++, "Alice Smith", "alice.smith@email.com"));
        members.add(new Member(nextMemberId++, "Bob Johnson", "0412 345 678"));
    }

    @Override
    public void addBook(Book book) {
        this.books.add(book);
    }

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

    @Override
    public void removeBook(String isbn) {
        this.books.removeIf(book -> book.getIsbn().equals(isbn));
        // In a real system, you'd need to handle cases where the book is on loan
    }

    @Override
    public void addMember(Member member) {
        member.setMemberId(nextMemberId++);
        this.members.add(member);
    }

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

    @Override
    public void removeMember(int memberId) {
        this.members.removeIf(member -> member.getMemberId() == memberId);
        // In a real system, you'd need to handle cases where the member has active
        // loans
    }

    @Override
    public void loanBook(int memberId, String isbn, String dueDate) {
        Member member = getMemberById(memberId);
        Book book = getBookByIsbn(isbn);
        if (member != null && book != null && !isBookLoaned(isbn)) {
            this.loans.add(new Loan(nextLoanId++, memberId, isbn, dueDate));
        } else {
            System.out.println("Error: Could not loan book. Member or book not found, or book is already on loan.");
            // In a real UI, you'd provide better feedback
        }
    }

    private boolean isBookLoaned(String isbn) {
        for (Loan loan : this.loans) {
            if (loan.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void returnBook(String isbn) {
        this.loans.removeIf(loan -> loan.getIsbn().equals(isbn));
    }

    @Override
    public List<Book> listAllBooks() {
        return new ArrayList<>(this.books); // Return a copy to prevent external modification
    }

    @Override
    public List<Member> listAllMembers() {
        return new ArrayList<>(this.members); // Return a copy
    }

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

    // Helper methods to find Book and Member by ID/ISBN
    public Book getBookByIsbn(String isbn) {
        for (Book book : this.books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public Member getMemberById(int memberId) {
        for (Member member : this.members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }

    // Helper method to get a Loan by ISBN (you might need this for returnBook)
    public Loan getLoanByIsbn(String isbn) {
        for (Loan loan : this.loans) {
            if (loan.getIsbn().equals(isbn)) {
                return loan;
            }
        }
        return null;
    }
}