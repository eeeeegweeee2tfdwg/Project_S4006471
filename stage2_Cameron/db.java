package stage2_Cameron;

import java.util.ArrayList;
import java.util.List;

public class db{
    public static List<Book> getAllBooksFromDB(){
        List<Book> booksFromDB = new ArrayList<>();
        booksFromDB.add(new Book("978-0007140632", "Harry Potter and the Philosopher's Stone", "J.K. Rowling"));
        booksFromDB.add(new Book("978-0743273565", "The Great Gatsby", "F. Scott Fitzgerald"));
        booksFromDB.add(new Book("978-0061120084", "To Kill a Mockingbird", "Harper Lee"));
        booksFromDB.add(new Book("978-0141439518", "Pride and Prejudice", "Jane Austen"));
        return booksFromDB;
    }

    public static List<Member> getAllMembersFromDB(){
        List<Member> membersFromDB = new ArrayList<>();
        membersFromDB.add(new Member(101, "Sophia Williams", "sophia.w@email.com"));
        membersFromDB.add(new Member(102, "Ethan Brown", "0422 987 654"));
        membersFromDB.add(new Member(103, "Olivia Davis", "olivia.d@phone.net"));
        membersFromDB.add(new Member(104, "Noah Garcia", "noah.g@home.com"));
        return membersFromDB;
    }
}