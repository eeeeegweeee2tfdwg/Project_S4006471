package stage2_Cameron;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The main Graphical User Interface for the MelLibrary System.
 * Provides tabs for Library Operations and a "Database View" to show current
 * in-memory data.
 */
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

    /**
     * Constructor for the UserInterface class.
     * Initializes the MelLibrary system and sets up the Swing GUI components.
     */
    @SuppressWarnings("Convert2Lambda")
    public UserInterface() {
        this.librarySystem = new MelLibrary(); // Initialize MelLibrary, which loads initial data
        setTitle("MelLibrary System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Increased size for better layout
        setLocationRelativeTo(null); // Center the window

        tabbedPane = new JTabbedPane();

        // --- Library Operations Tab ---
        libraryOperationsPanel = new JPanel();
        libraryOperationsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Add gaps
        libraryOperationsPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        createLibraryOperationsTab();
        tabbedPane.addTab("Library Operations", libraryOperationsPanel);

        // --- Database View Tab ---
        databaseViewPanel = new JPanel();
        databaseViewPanel.setLayout(new GridLayout(1, 2, 10, 10)); // Grid layout for two text areas
        databaseViewPanel.setBackground(new Color(255, 255, 240)); // Ivory background

        booksTextArea = new JTextArea();
        booksTextArea.setEditable(false); // Make it read-only
        booksTextArea.setFont(new Font("Comic Sans", Font.PLAIN, 12));
        booksTextArea.setForeground(new Color(0, 100, 0)); // Dark green text

        membersTextArea = new JTextArea();
        membersTextArea.setEditable(false); // Make it read-only
        membersTextArea.setFont(new Font("Comic Sans", Font.PLAIN, 12));
        membersTextArea.setForeground(new Color(0, 100, 0)); // Dark green text

        // Populate initial content for the Database View
        createDatabaseViewTabContent();

        databaseViewPanel.add(new JScrollPane(booksTextArea));
        databaseViewPanel.add(new JScrollPane(membersTextArea));
        tabbedPane.addTab("Current Data View", databaseViewPanel); // Renamed tab for clarity

        // Add ChangeListener to refresh data when the Database View tab is selected
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Refresh the database view when its tab is selected
                if (tabbedPane.getSelectedIndex() == tabbedPane.indexOfTab("Current Data View")) {
                    refreshDatabaseView();
                }
            }
        });

        add(tabbedPane);
        setVisible(true);
    }

    /**
     * Populates the Library Operations tab with various buttons for library
     * management.
     */
    private void createLibraryOperationsTab() {
        // Helper to create uniform buttons
        ActionListener commonRefreshAction = _ -> refreshDatabaseView(); // Lambda for common refresh

        // Add Book Button
        JButton addBookButton = createStyledButton("Add Book", new Color(173, 216, 230), Color.BLACK);
        libraryOperationsPanel.add(addBookButton);
        addBookButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN:");
            if (isbn != null && !isbn.trim().isEmpty()) {
                String title = JOptionPane.showInputDialog(UserInterface.this, "Enter Title:");
                if (title != null && !title.trim().isEmpty()) {
                    String author = JOptionPane.showInputDialog(UserInterface.this, "Enter Author:");
                    if (author != null && !author.trim().isEmpty()) {
                        Book newBook = new Book(isbn.trim(), title.trim(), author.trim());
                        librarySystem.addBook(newBook);
                        JOptionPane.showMessageDialog(UserInterface.this,
                                "Book '" + title.trim() + "' added successfully!");
                        commonRefreshAction.actionPerformed(e); // Refresh view
                    } else if (author != null) { // Author was input but empty
                        JOptionPane.showMessageDialog(UserInterface.this, "Author cannot be empty.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (title != null) { // Title was input but empty
                    JOptionPane.showMessageDialog(UserInterface.this, "Title cannot be empty.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (isbn != null) { // ISBN was input but empty
                JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete Book Button
        JButton deleteBookButton = createStyledButton("Delete Book", new Color(255, 17, 0), Color.RED);
        libraryOperationsPanel.add(deleteBookButton);
        deleteBookButton.addActionListener(e -> {
            String isbnToDelete = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN of book to delete:");
            if (isbnToDelete != null && !isbnToDelete.trim().isEmpty()) {
                Book book = librarySystem.getBookByIsbn(isbnToDelete.trim());
                if (book != null) {
                    librarySystem.removeBook(isbnToDelete.trim());
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "Book '" + book.getTitle() + "' (ISBN: " + isbnToDelete.trim() + ") deleted successfully!");
                    commonRefreshAction.actionPerformed(e); // Refresh view
                } else {
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "No book found with ISBN: " + isbnToDelete.trim(), "Book Not Found",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else if (isbnToDelete != null) {
                JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add Member Button
        JButton addMemberButton = createStyledButton("Add Member", new Color(173, 216, 230), Color.BLACK);
        libraryOperationsPanel.add(addMemberButton);
        addMemberButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(UserInterface.this, "Enter member's name:");
            if (name != null && !name.trim().isEmpty()) {
                String contact = JOptionPane.showInputDialog(UserInterface.this,
                        "Enter member's contact (email or phone):");
                if (contact != null && !contact.trim().isEmpty()) {
                    Member newMember = new Member(0, name.trim(), contact.trim()); // ID 0 to let MelLibrary assign
                    librarySystem.addMember(newMember);
                    JOptionPane.showMessageDialog(UserInterface.this, "Member '" + name.trim()
                            + "' added successfully with ID: " + newMember.getMemberId() + "!");
                    commonRefreshAction.actionPerformed(e); // Refresh view
                } else if (contact != null) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Contact information cannot be empty.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (name != null) {
                JOptionPane.showMessageDialog(UserInterface.this, "Name cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete Member Button
        JButton deleteMemberButton = createStyledButton("Delete Member", new Color(255, 17, 0), Color.RED);
        libraryOperationsPanel.add(deleteMemberButton);
        deleteMemberButton.addActionListener(e -> {
            String memberIdStr = JOptionPane.showInputDialog(UserInterface.this, "Enter ID of member to delete:");
            if (memberIdStr != null && !memberIdStr.trim().isEmpty()) {
                try {
                    int memberIdToDelete = Integer.parseInt(memberIdStr.trim());
                    Member memberToDelete = librarySystem.getMemberById(memberIdToDelete);
                    if (memberToDelete != null) {
                        librarySystem.removeMember(memberIdToDelete);
                        JOptionPane.showMessageDialog(UserInterface.this,
                                "Member '" + memberToDelete.getName() + "' (ID: " + memberIdToDelete
                                        + ") deleted successfully!");
                        commonRefreshAction.actionPerformed(e); // Refresh view
                    } else {
                        JOptionPane.showMessageDialog(UserInterface.this,
                                "No member found with ID " + memberIdToDelete + ".", "Invalid ID",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "Invalid Member ID format. Please enter a number.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (memberIdStr != null) {
                JOptionPane.showMessageDialog(UserInterface.this, "Member ID cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // List All Books Button
        JButton listBooksButton = createStyledButton("List All Books", new Color(220, 220, 220), Color.BLACK);
        libraryOperationsPanel.add(listBooksButton);
        listBooksButton.addActionListener(_ -> {
            List<Book> allBooks = librarySystem.listAllBooks();
            if (allBooks.isEmpty()) {
                JOptionPane.showMessageDialog(UserInterface.this, "No books in the library.");
            } else {
                StringBuilder sb = new StringBuilder("All Books:\n");
                for (Book book : allBooks) {
                    sb.append(book.toString()).append("\n"); // Using toString()
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300)); // Set preferred size for scroll pane
                JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Library Books",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // List All Members Button
        JButton listMembersButton = createStyledButton("List All Members", new Color(220, 220, 220), Color.BLACK);
        libraryOperationsPanel.add(listMembersButton);
        listMembersButton.addActionListener(_ -> {
            List<Member> allMembers = librarySystem.listAllMembers();
            if (allMembers.isEmpty()) {
                JOptionPane.showMessageDialog(UserInterface.this, "No members registered.");
            } else {
                StringBuilder sb = new StringBuilder("All Members:\n");
                // Sort members by name before displaying
                Collections.sort(allMembers, Comparator.comparing(Member::getName, String.CASE_INSENSITIVE_ORDER));
                for (Member member : allMembers) {
                    sb.append(member.toString()).append("\n"); // Using toString()
                }
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 300)); // Set preferred size for scroll pane
                JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Library Members",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Search Book Button
        JButton searchBookButton = createStyledButton("Search Book", new Color(220, 220, 220), Color.BLACK);
        libraryOperationsPanel.add(searchBookButton);
        searchBookButton.addActionListener(_ -> {
            String query = JOptionPane.showInputDialog(UserInterface.this,
                    "Enter search term (ISBN, Title, or Author):");
            if (query != null && !query.trim().isEmpty()) {
                List<Book> results = librarySystem.searchBook(query.trim());
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "No books found matching your search '" + query.trim() + "'.");
                } else {
                    StringBuilder sb = new StringBuilder("Search Results for \"" + query.trim() + "\":\n");
                    for (Book book : results) {
                        sb.append(book.toString()).append("\n"); // Using toString()
                    }
                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));
                    JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Search Results",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Search Member Button
        JButton searchMemberButton = createStyledButton("Search Member", new Color(220, 220, 220), Color.BLACK);
        libraryOperationsPanel.add(searchMemberButton);
        searchMemberButton.addActionListener(_ -> {
            String query = JOptionPane.showInputDialog(UserInterface.this,
                    "Enter search term (ID, Name, or Contact):");
            if (query != null && !query.trim().isEmpty()) {
                List<Member> results = librarySystem.searchMember(query.trim());
                if (results.isEmpty()) {
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "No members found matching your search '" + query.trim() + "'.");
                } else {
                    StringBuilder sb = new StringBuilder("Search Results for \"" + query.trim() + "\":\n");
                    for (Member member : results) {
                        sb.append(member.toString()).append("\n"); // Using toString()
                    }
                    JTextArea textArea = new JTextArea(sb.toString());
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));
                    JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Search Results",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Loan Book Button
        JButton loanBookButton = createStyledButton("Loan Book", new Color(200, 200, 200), Color.BLACK);
        libraryOperationsPanel.add(loanBookButton);
        loanBookButton.addActionListener(_ -> {
            String memberIdStr = JOptionPane.showInputDialog(UserInterface.this, "Enter Member ID to loan to:");
            if (memberIdStr != null && !memberIdStr.trim().isEmpty()) {
                try {
                    int memberId = Integer.parseInt(memberIdStr.trim());
                    // Check if member exists
                    if (librarySystem.getMemberById(memberId) == null) {
                        JOptionPane.showMessageDialog(UserInterface.this,
                                "Member with ID " + memberId + " does not exist.", "Invalid Member ID",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String isbn = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN of book to loan:");
                    if (isbn != null && !isbn.trim().isEmpty()) {
                        // Check if book exists
                        if (librarySystem.getBookByIsbn(isbn.trim()) == null) {
                            JOptionPane.showMessageDialog(UserInterface.this,
                                    "Book with ISBN " + isbn.trim() + " does not exist.", "Invalid Book ISBN",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String dueDate = JOptionPane.showInputDialog(UserInterface.this,
                                "Enter Due Date (YYYY-MM-DD):");
                        if (dueDate != null && !dueDate.trim().isEmpty()) {
                            librarySystem.loanBook(memberId, isbn.trim(), dueDate.trim());
                            JOptionPane.showMessageDialog(UserInterface.this, "Book (ISBN: " + isbn.trim()
                                    + ") loaned to member ID " + memberId + " with due date: " + dueDate.trim());
                        } else if (dueDate != null) {
                            JOptionPane.showMessageDialog(UserInterface.this, "Due Date cannot be empty.",
                                    "Input Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (isbn != null) {
                        JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "Invalid Member ID format. Please enter a number.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (memberIdStr != null) {
                JOptionPane.showMessageDialog(UserInterface.this, "Member ID cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Return Book Button
        JButton returnBookButton = createStyledButton("Return Book", new Color(200, 200, 200), Color.BLACK);
        libraryOperationsPanel.add(returnBookButton);
        returnBookButton.addActionListener(_ -> {
            String isbn = JOptionPane.showInputDialog(UserInterface.this, "Enter ISBN of book to return:");
            if (isbn != null && !isbn.trim().isEmpty()) {
                Loan loan = librarySystem.getLoanByIsbn(isbn.trim());
                if (loan != null) {
                    librarySystem.returnBook(isbn.trim());
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "Book (ISBN: " + isbn.trim() + ") returned successfully.");
                } else {
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "Book with ISBN " + isbn.trim() + " is not currently on loan.", "No Active Loan",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else if (isbn != null) {
                JOptionPane.showMessageDialog(UserInterface.this, "ISBN cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // List Member Loans Button
        JButton listMemberLoansButton = createStyledButton("List Member Loans", new Color(200, 200, 200), Color.BLACK);
        libraryOperationsPanel.add(listMemberLoansButton);
        listMemberLoansButton.addActionListener(_ -> {
            String memberIdStr = JOptionPane.showInputDialog(UserInterface.this,
                    "Enter Member ID to list loans for:");
            if (memberIdStr != null && !memberIdStr.trim().isEmpty()) {
                try {
                    int memberId = Integer.parseInt(memberIdStr.trim());
                    Member member = librarySystem.getMemberById(memberId);
                    if (member == null) {
                        JOptionPane.showMessageDialog(UserInterface.this, "No member found with ID " + memberId + ".",
                                "Invalid Member ID", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    List<Loan> loans = librarySystem.listMemberLoans(memberId);
                    if (loans.isEmpty()) {
                        JOptionPane.showMessageDialog(UserInterface.this,
                                "No books currently loaned by member '" + member.getName() + "' (ID: " + memberId
                                        + ").");
                    } else {
                        StringBuilder sb = new StringBuilder(
                                "Loans for Member '" + member.getName() + "' (ID: " + memberId + "):\n");
                        for (Loan loan : loans) {
                            sb.append(loan.toString()).append("\n"); // Using toString()
                        }
                        JTextArea textArea = new JTextArea(sb.toString());
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(500, 300));
                        JOptionPane.showMessageDialog(UserInterface.this, scrollPane, "Member Loans",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UserInterface.this,
                            "Invalid Member ID format. Please enter a number.", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (memberIdStr != null) {
                JOptionPane.showMessageDialog(UserInterface.this, "Member ID cannot be empty.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Helper method to create a JButton with specified background and foreground
     * colors.
     * 
     * @param text    The text to display on the button.
     * @param bgColor The background color of the button.
     * @param fgColor The foreground (text) color of the button.
     * @return A styled JButton.
     */
    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false); // Remove focus border
        button.setPreferredSize(new Dimension(150, 30)); // Set a preferred size
        return button;
    }

    /**
     * Populates the "Current Data View" tab with current lists of books and members
     * by fetching data from the in-memory MelLibrary system.
     */
    private void createDatabaseViewTabContent() {
        // Books Display
        List<Book> allBooks = librarySystem.listAllBooks();
        booksTextArea.setText("--- All Books in Library ---\n\n"); // Clear and add header
        // Sort books by title for consistent display
        Collections.sort(allBooks, Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
        if (allBooks.isEmpty()) {
            booksTextArea.append("No books currently in the library.\n");
        } else {
            for (Book book : allBooks) {
                booksTextArea.append(book.toString() + "\n"); // Use toString()
            }
        }

        // Members Display
        List<Member> allMembers = librarySystem.listAllMembers();
        membersTextArea.setText("--- All Registered Members ---\n\n"); // Clear and add header
        // Sort members by name for consistent display
        Collections.sort(allMembers, Comparator.comparing(Member::getName, String.CASE_INSENSITIVE_ORDER));
        if (allMembers.isEmpty()) {
            membersTextArea.append("No members currently registered.\n");
        } else {
            for (Member member : allMembers) {
                membersTextArea.append(member.toString() + "\n"); // Use toString()
            }
        }
    }

    /**
     * Refreshes the content of the "Current Data View" tab.
     */
    private void refreshDatabaseView() {
        createDatabaseViewTabContent(); // Reload the data
    }

    /**
     * Main method to run the MelLibrary User Interface application.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Ensure GUI updates are done on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new UserInterface());
    }
}