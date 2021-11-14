package fr.univtln.bourdeszamora;

/**
 * This interface is used to represent the transversal properties of the Book and Laptop classes
 */
public interface Borrowable {
    boolean isAvailable();
    void setAvailable(boolean available);
}
