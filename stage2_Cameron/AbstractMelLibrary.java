package stage2_Cameron;

import java.util.List;

public abstract class AbstractMelLibrary {
    public abstract void addBook(Book book);
    public abstract List<Book> searchBook(String query);
    public abstract void removeBook(String isbn);
    public abstract void addMember(Member member);
    public abstract List<Member> searchMember(String query);
    public abstract void removeMember(int memberId);
    public abstract loanBook(int memberId, String isbn, String dueDate);
    public abstract void returnBook(String isbn);
    public abstract List<Book> listAllBooks();
    public abstract List<Member> listAllMembers();
    public abstract List<Loan> listMemberLoans(int memberId);
}