package stage2_Cameron;

/**
 * Represents a member of the MelLibrary system.
 */
public class Member {
    private int memberId;
    private String name;
    private String contact; // Could be email or phone number

    /**
     * Default constructor for the Member class.
     * Initializes attributes to default values.
     */
    public Member() {
        this.memberId = 0; // Or a sentinel value like -1 to indicate unassigned ID
        this.name = "";
        this.contact = "";
    }

    /**
     * Parameterized constructor for the Member class.
     * 
     * @param memberId The unique ID of the member.
     * @param name     The full name of the member.
     * @param contact  The contact information (email or phone) of the member.
     */
    public Member(int memberId, String name, String contact) {
        this.memberId = memberId;
        this.name = name;
        this.contact = contact;
    }

    /**
     * Gets the unique ID of the member.
     * 
     * @return The member ID.
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * Sets the unique ID of the member.
     * 
     * @param memberId The new member ID.
     */
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    /**
     * Gets the full name of the member.
     * 
     * @return The member's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the member.
     * 
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the contact information of the member.
     * 
     * @return The member's contact (email or phone).
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the contact information of the member.
     * 
     * @param contact The new contact information.
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Returns a string representation of the Member object.
     * 
     * @return A string containing the member's ID, name, and contact information.
     */
    @Override
    public String toString() {
        return "ID: " + memberId + ", Name: " + name + ", Contact: " + contact;
    }
}