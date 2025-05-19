package stage2_Cameron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserInterface extends JFrame {

    @SuppressWarnings({"unused", "FieldMayBeFinal"})
    private MelLibrary librarySystem;
    @SuppressWarnings("FieldMayBeFinal")
    private JTabbedPane tabbedPane;
    @SuppressWarnings("FieldMayBeFinal")
    private JPanel libraryOperationsPanel;
    @SuppressWarnings("FieldMayBeFinal")
    private JPanel databaseViewPanel;

    public UserInterface() {
        this.librarySystem = new MelLibrary();
        setTitle("MelLibrary System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        libraryOperationsPanel = new JPanel();
        libraryOperationsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        createLibraryOperationsTab();
        tabbedPane.addTab("Library Operations", libraryOperationsPanel);

        databaseViewPanel = new JPanel();
        databaseViewPanel.setLayout(new BorderLayout());
        createDatabaseViewTab();
        tabbedPane.addTab("Database View", databaseViewPanel);

        add(tabbedPane);
        setVisible(true);
    }

    @SuppressWarnings("Convert2Lambda")
    private void createLibraryOperationsTab() {
        JButton addBookButton = new JButton("Add Book");
        libraryOperationsPanel.add(addBookButton);

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(UserInterface.this,
                        "Add Book functionality (not fully implemented in this example).");
            }
        });

    }

    private void createDatabaseViewTab() {
        List<Book> allBooks = db.getAllBooksFromDB();
        JTextArea booksTextArea = new JTextArea();
        for (Book book : allBooks) {
            booksTextArea.append(book.getIsbn() + " - " + book.getTitle() + " by " + book.getAuthor() + "\n");
        }
        databaseViewPanel.add(new JScrollPane(booksTextArea), BorderLayout.CENTER);

        List<Member> allMembers = db.getAllMembersFromDB();
        JTextArea membersTextArea = new JTextArea();
        for (Member member : allMembers) {
            membersTextArea.append("ID: " + member.getMemberId() + ", Name: " + member.getName() + ", Contact: "
                    + member.getContact() + "\n");
        }
        databaseViewPanel.add(new JScrollPane(membersTextArea), BorderLayout.EAST);
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