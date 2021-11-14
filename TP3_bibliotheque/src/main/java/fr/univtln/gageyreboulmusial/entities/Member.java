package fr.univtln.gageyreboulmusial.entities;

import fr.univtln.gageyreboulmusial.Borrowable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(builderMethodName = "memberBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Entity {

    @NotNull(message = "Entity's id can't be null")
    private int id = -1;

    @NotEmpty(message = "Member must have a last name")
    private String name;

    @NotEmpty(message = "Member must have a first name")
    private String first_name;

    private Status status;

    @NotNull(message = "Member must have a valid Library")
    private int idLibrary;


    private Set<Borrowable> borrowingObject = new HashSet<Borrowable>();
    private final int NB_BORROWABLE_OBJECT = 5;

    /**
     * Enumerate type for Member status
     */
    public enum Status{
        STUDENT, TEACHER
    }

    /**
     * Allow a member to borrow a borrowable object
     * @param b A Borrowable instance
     * @return A boolean : true if the member borrowed the object and false if he doesn't
     * @see Borrowable
     */
    public boolean borrow(Borrowable b) {
        if (b.isAvailable() && this.borrowingObject.size() <= NB_BORROWABLE_OBJECT) {
            this.borrowingObject.add(b);
            b.setAvailable(false);
            return true;
        }
        return false;
    }

    /**
     * Allow a member to borrow a instance of LaptopComputer
     * @param l A LaptopComputer instance
     * @return A boolean : true if the member borrowed the laptop and false if he doesn't
     * @see LaptopComputer
     * @see Borrowable
     */
    public boolean borrow(LaptopComputer l) {
        for (Borrowable borrow_l : borrowingObject) {
            if(l instanceof LaptopComputer && l.isAvailable()){
                System.out.println("You already have an outstanding loan.");
                return false;
            }
        }
        if (l.isAvailable() && this.borrowingObject.size() <= NB_BORROWABLE_OBJECT) {
            this.borrowingObject.add(l);
            l.setAvailable(false);
            return true;
        }
        if(!l.isAvailable()){
            System.out.println("This computer is borrowed by another member.");
        }
        return false;
    }

    /**
     * Allow to restore a Borrowable object
     * @param b A Borrowable object
     * @return true
     * @see Borrowable
     */
    public boolean restore(Borrowable b) {
        b.setAvailable(true);
        this.borrowingObject.remove(b);
        return true;
    }

    /**
     * Allow to restore LaptopComputer object
     * @param l A LaptopComputer instance
     * @return true
     * @see LaptopComputer
     */
    public boolean restore(LaptopComputer l) {
        l.setAvailable(true);
        this.borrowingObject.remove(l);
        return true;
    }

    /**
     * Getter of class private attribute borrowingObject
     * @return a set of borrowable object
     * @see Set
     * @see Borrowable
     */
    public Set<Borrowable> getBorrowingObject() {
        return borrowingObject;
    }

    /**
     * Display all Borrowable object of the Member instance
     */
    public void displayBorrowing() {
        System.out.println("Member Borrowing");
        System.out.println(this.toString());
        for (Borrowable b:
                this.borrowingObject) {
            System.out.println(b.toString());
        }
    }
}