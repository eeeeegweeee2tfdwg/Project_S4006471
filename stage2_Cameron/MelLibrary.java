package stage2_Cameron;

import java.util.ArrayList;
import java.util.List;

public class MelLibrary extends AbstractMelLibrary{
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    private ArrayList<Loan> loans;
    private int nextMemberId;
    private int nextLoanId;

    public MelLibrary() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
        initalizeData();
    }

    private void initalizeData() {
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
    public List<Book> searchBook(String query){
        List<Book> results = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();
        for (Book book : this.books) {
            if (book.getIsbn().toLowerCase().contains(lowerCaseQuery)) ||
            book.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                    book.getAuthor().toLowerCase().contains(lowerCaseQuery){
                results.add(book);
            }
        }
    }

    @Override
    public void removeBook(String isbn) {
        this.books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public void addMember(Member member){
        member.setMemberId(nextMemberId++);
        this.members.add(member);
    }
}