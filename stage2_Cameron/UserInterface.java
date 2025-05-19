package stage2_Cameron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserInterface extends JFrame {

    @SuppressWarnings("FieldMayBeFinal")
    private MelLibrary librarySystem;
    private JTabbedPane tabbedPane;
    @SuppressWarnings("FieldMayBeFinal")
    private JPanel libraryOperationsPanel;
    @SuppressWarnings("FieldMayBeFinal")
    private JPanel databaseViewPanel;
    @SuppressWarnings("FieldMayBeFinal")
    private JTextArea booksTextArea;
    @SuppressWarnings("FieldMayBeFinal")
    private JTextArea membersTextArea;

    @SuppressWarnings("Convert2Lambda")
    public UserInterface() {
        this.librarySystem = new MelLibrary(); // Initialize MelLibrary directly
        setTitle("MelLibrary System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Create the Library Operations Tab
        libraryOperationsPanel = new JPanel();
        libraryOperationsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        createLibraryOperationsTab();
        tabbedPane.addTab("Library Operations", libraryOperationsPanel);

        // Create the Database View Tab (initialize components)
        databaseViewPanel = new JPanel();
        databaseViewPanel.setLayout(new BorderLayout());
        booksTextArea = new JTextArea();
        membersTextArea = new JTextArea();
        createDatabaseViewTabContent(); // Load initial data
        booksTextArea.setForeground(new Color(0, 100, 0)); // Dark green
        membersTextArea.setForeground(new Color(0, 100, 0));
        databaseViewPanel.add(new JScrollPane(booksTextArea), BorderLayout.CENTER);
        databaseViewPanel.add(new JScrollPane(membersTextArea), BorderLayout.EAST);
        tabbedPane.addTab("Database View", databaseViewPanel);

        // Add ChangeListener to refresh data when the Database View tab is selected
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 1) { // Index 1 is the Database View tab
                    refreshDatabaseView();
                }
            }
        });

        add(tabbedPane);
        setVisible(true);
    }

    @SuppressWarnings("Convert2Lambda")
    private void createLibraryOperationsTab() {
        // Add Book Button
        JButton addBookButton = new JButton("Add Book");
        libraryOperationsPanel.add(addBookButton);
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN:");
                if (isbn != null && !isbn.trim().isEmpty()) {
                    String title = JOptionPane.showInputDialog(UserInterface.this, "Enter Title:");
                    if (title != null && !title.trim().isEmpty()) {
                        String author = JOptionPane.showInputDialog(UserInterface.this, "Enter Author:");
                        if (author != null && !author.trim().isEmpty()) {
                            Book newBook = new Book(isbn, title, author);
                            librarySystem.addBook(newBook);
                            refreshDatabaseView();
                            JOptionPane.showMessageDialog(UserInterface.this,
                                    "Book '" + title + "' added successfully!");

                        } else if (author != null) {
                            JOptionPane.showMessageDialog(UserInterface.this, "Author cannot be empty.", "Input Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (title != null) {
                        JOptionPane.showMessageDialog(UserInterface.this, "Title cannot be empty.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (isbn != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton deleteBookButton = new JButton("Delete Book");
        libraryOperationsPanel.add(deleteBookButton);
        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbnToDelete = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN of book to delete:");
                if (isbnToDelete != null && !isbnToDelete.trim().isEmpty()) {
                    librarySystem.removeBook(isbnToDelete);
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "Book with ISBN '" + isbnToDelete + "' deleted successfully!");
                    refreshDatabaseView(); // Refresh the database view
                } else if (isbnToDelete != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add Member Button
        JButton addMemberButton = new JButton("Add Member");
        libraryOperationsPanel.add(addMemberButton);
        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(UserInterface.this, "Enter member's name:");
                if (name != null && !name.trim().isEmpty()) {
                    String contact = JOptionPane.showInputDialog(UserInterface.this,
                            "Enter member's contact (email or phone):");
                    if (contact != null && !contact.trim().isEmpty()) {
                        Member newMember = new Member(0, name, contact);
                        librarySystem.addMember(newMember);
                        JOptionPane.showMessageDialog(UserInterface.this, "Member '" + name + "' added successfully!");
                        refreshDatabaseView(); // Refresh the Database View after adding a member
                    } else if (contact != null) {
                        JOptionPane.showMessageDialog(UserInterface.this, "Contact information cannot be empty.",
                                "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (name != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Name cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton deleteMemberButton = new JButton("Delete Member");
        libraryOperationsPanel.add(deleteMemberButton);
        deleteMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberIdStr = JOptionPane.showInputDialog(UserInterface.this, "Enter ID of member to delete:");
                if (memberIdStr != null && !memberIdStr.trim().isEmpty()) {
                    try {
                        int memberIdToDelete = Integer.parseInt(memberIdStr);
                        Member memberToDelete = librarySystem.getMemberById(memberIdToDelete);
                        if (memberToDelete != null) {
                            librarySystem.removeMember(memberIdToDelete);
                            JOptionPane.showMessageDialog(UserInterface.this,
                                    "Member with ID " + memberIdToDelete + " deleted successfully!");
                            refreshDatabaseView(); // Refresh the Database View after deleting a member
                        } else {
                            JOptionPane.showMessageDialog(UserInterface.this,
                                    "No member found with ID " + memberIdToDelete + ".", "Invalid ID",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(UserInterface.this, "Invalid Member ID format.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (memberIdStr != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Member ID cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // List All Books Button
        JButton listBooksButton = new JButton("List All Books");
        libraryOperationsPanel.add(listBooksButton);
        listBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Book> allBooks = librarySystem.listAllBooks();
                if (allBooks.isEmpty()) {
                    JOptionPane.showMessageDialog(UserInterface.this, "No books in the library.");
                } else {
                    StringBuilder sb = new StringBuilder("All Books:\n");
                    for (Book book : allBooks) {
                        sb.append(book.getIsbn()).append(" - ").append(book.getTitle()).append(" by ")
                                .append(book.getAuthor()).append("\n");
                    }
                    JTextArea textArea = new JTextArea(sb.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Library Books",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // List All Members Button
        JButton listMembersButton = new JButton("List All Members");
        libraryOperationsPanel.add(listMembersButton);
        listMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Member> allMembers = librarySystem.listAllMembers();
                if (allMembers.isEmpty()) {
                    JOptionPane.showMessageDialog(UserInterface.this, "No members registered.");
                } else {
                    StringBuilder sb = new StringBuilder("All Members:\n");
                    for (Member member : allMembers) {
                        sb.append("ID: ").append(member.getMemberId()).append(", Name: ").append(member.getName())
                                .append(", Contact: ").append(member.getContact()).append("\n");
                    }
                    JTextArea textArea = new JTextArea(sb.toString());
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Library Members",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Search Book Button
        JButton searchBookButton = new JButton("Search Book");
        libraryOperationsPanel.add(searchBookButton);
        searchBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = JOptionPane.showInputDialog(UserInterface.this,
                        "Enter search term (ISBN, Title, or Author):");
                if (query != null && !query.trim().isEmpty()) {
                    List<Book> results = librarySystem.searchBook(query);
                    if (results.isEmpty()) {
                        JOptionPane.showMessageDialog(UserInterface.this, "No books found matching your search.");
                    } else {
                        StringBuilder sb = new StringBuilder("Search Results:\n");
                        for (Book book : results) {
                            sb.append(book.getIsbn()).append(" - ").append(book.getTitle()).append(" by ")
                                    .append(book.getAuthor()).append("\n");
                        }
                        JTextArea textArea = new JTextArea(sb.toString());
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Search Results",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // Search Member Button
        JButton searchMemberButton = new JButton("Search Member");
        libraryOperationsPanel.add(searchMemberButton);
        searchMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = JOptionPane.showInputDialog(UserInterface.this,
                        "Enter search term (ID, Name, or Contact):");
                if (query != null && !query.trim().isEmpty()) {
                    List<Member> results = librarySystem.searchMember(query);
                    if (results.isEmpty()) {
                        JOptionPane.showMessageDialog(UserInterface.this, "No members found matching your search.");
                    } else {
                        StringBuilder sb = new StringBuilder("Search Results:\n");
                        for (Member member : results) {
                            sb.append("ID: ").append(member.getMemberId()).append(", Name: ").append(member.getName())
                                    .append(", Contact: ").append(member.getContact()).append("\n");
                        }
                        JTextArea textArea = new JTextArea(sb.toString());
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Search Results",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // Loan Book Button
        JButton loanBookButton = new JButton("Loan Book");
        libraryOperationsPanel.add(loanBookButton);
        loanBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberIdStr = JOptionPane.showInputDialog(UserInterface.this, "Enter Member ID to loan to:");
                if (memberIdStr != null && !memberIdStr.trim().isEmpty()) {
                    try {
                        int memberId = Integer.parseInt(memberIdStr);
                        String isbn = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN of book to loan:");
                        if (isbn != null && !isbn.trim().isEmpty()) {
                            String dueDate = JOptionPane.showInputDialog(UserInterface.this,
                                    "Enter Due Date (e.g.,YYYY-MM-DD):");
                            if (dueDate != null && !dueDate.trim().isEmpty()) {
                                librarySystem.loanBook(memberId, isbn, dueDate);
                                JOptionPane.showMessageDialog(UserInterface.this, "Book with ISBN '" + isbn
                                        + "' loaned to member ID " + memberId + " with due date: " + dueDate);
                            } else if (dueDate != null) {
                                JOptionPane.showMessageDialog(UserInterface.this, "Due Date cannot be empty.",
                                        "Input Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (isbn != null) {
                            JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(UserInterface.this, "Invalid Member ID format.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (memberIdStr != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Member ID cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Return Book Button
        JButton returnBookButton = new JButton("Return Book");
        libraryOperationsPanel.add(returnBookButton);
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN of book to return:");
                if (isbn != null && !isbn.trim().isEmpty()) {
                    librarySystem.returnBook(isbn);
                    JOptionPane.showMessageDialog(UserInterface.this, "Book with ISBN '" + isbn + "' returned.");
                } else if (isbn != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // List Member Loans Button
        JButton listMemberLoansButton = new JButton("List Member Loans");
        libraryOperationsPanel.add(listMemberLoansButton);
        listMemberLoansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberIdStr = JOptionPane.showInputDialog(UserInterface.this,
                        "Enter Member ID to list loans for:");
                if (memberIdStr != null && !memberIdStr.trim().isEmpty()) {
                    try {
                        int memberId = Integer.parseInt(memberIdStr);
                        List<Loan> loans = librarySystem.listMemberLoans(memberId);
                        if (loans.isEmpty()) {
                            JOptionPane.showMessageDialog(UserInterface.this,
                                    "No books currently loaned by member ID " + memberId);
                        } else {
                            StringBuilder sb = new StringBuilder("Loans for Member ID " + memberId + ":\n");
                            for (Loan loan : loans) {
                                sb.append("Loan ID: ").append(loan.getLoanId())
                                        .append(", ISBN: ").append(loan.getIsbn())
                                        .append(", Due Date: ").append(loan.getDueDate())
                                        .append("\n");
                            }
                            JTextArea textArea = new JTextArea(sb.toString());
                            JScrollPane scrollPane = new JScrollPane(textArea);
                            JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Member Loans",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(UserInterface.this, "Invalid Member ID format.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (memberIdStr != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Member ID cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @SuppressWarnings("Convert2Lambda")
    private void createDatabaseViewTabContent() {
        // Load data from MelLibrary and populate text areas
        List<Book> allBooks = librarySystem.listAllBooks();
        booksTextArea.setText(""); // Clear previous content
        for (Book book : allBooks) {
            booksTextArea.append(book.getIsbn() + " - " + book.getTitle() + " by " + book.getAuthor() + "\n");
        }

        List<Member> allMembers = librarySystem.listAllMembers(); // Get members from MelLibrary
        // Sort members by name
        Collections.sort(allMembers, new Comparator<Member>() {
            @Override
            public int compare(Member m1, Member m2) {
                return m1.getName().compareToIgnoreCase(m2.getName());
            }
        });
        membersTextArea.setText(""); // Clear previous content
        for (Member member : allMembers) {
            membersTextArea.append("ID: " + member.getMemberId() + ", Name: " + member.getName() + ", Contact: "
                    + member.getContact() + "\n");
        }
    }

    private void refreshDatabaseView() {
        createDatabaseViewTabContent(); // Reload the data
    }

    @SuppressWarnings("Convert2Lambda")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @SuppressWarnings("override")
            public void run() {
                new UserInterface();
            }
        });
    }
}
