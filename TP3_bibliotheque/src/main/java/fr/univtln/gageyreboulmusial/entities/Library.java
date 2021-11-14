package fr.univtln.gageyreboulmusial.entities;

import fr.univtln.gageyreboulmusial.Borrowable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder(builderMethodName = "libraryBuilder")
@AllArgsConstructor
@NoArgsConstructor
public class Library implements Entity{

    @NotNull(message = "Entity's id can't be null")
    private int id = -1;

    Set<Member> CollecMember = new HashSet<Member>();
    Set<Document> CollecDocument = new HashSet<Document>();
    Set<Material> CollecMaterial = new HashSet<Material>();

    /**
     * This function adds a member to our library
     * @param member library's member
     * @return a Collection of library's member
     */
    public boolean addMember(Member member) {
        return CollecMember.add(member);
    }

    /**
     * This function adds a document to our library
     * @param document library's document
     * @return a Collection of library's document
     */
    public boolean addDocument(Document document) {
        return CollecDocument.add(document);
    }

    /**
     * This function adds a material to our library
     * @param material library's material
     * @return a Collection of library's material
     */
    public boolean addMaterial(Material material) {
        return CollecMaterial.add(material);
    }

    /**
     * This function indicates which member has borrowed the item "d"
     * @param d borrowed item
     */
    public void borrowedBy(Borrowable d) {
        System.out.println("Borrow by : ");
        for (Member m :
                CollecMember) {
            if (m.getBorrowingObject().contains(d)) {
                System.out.println(m.toString());
            }
        }
    }

    /**
     * This function lists all the objects in the library.
     * If they have been borrowed, the function displays
     * who has borrowed the object
     */
    public void displayFunds() {
        System.out.println("Funds : \nDOCUMENTS : ");
        for (Document d:
                CollecDocument) {
            System.out.println(d.toString());
            if(d instanceof Borrowable) {
                this.borrowedBy((Borrowable) d);
            }
        }

        System.out.println("MATERIALS : ");
        for (Material m:
                CollecMaterial) {
            System.out.println(m.toString());
            if(m instanceof Borrowable) {
                this.borrowedBy((Borrowable) m);
            }
        }
    }

    /**
     * This function searches and displays documents
     * that contain in their title the past word in attribute
     * @param word word to search for a document title
     * @return a Set of documents with compatible titles
     */
    public Set<Document> searchTitle(String word){
        Set<Document> SearchDocument = new HashSet<Document>();
        for (Document document_itr: CollecDocument) {
            String getTitleUpper = document_itr.getTitle().toUpperCase();
            String wordUpper = word.toUpperCase();
            int index = getTitleUpper.indexOf(wordUpper);
            if(index != -1){
                SearchDocument.add(document_itr);
            }
        }
        return SearchDocument;
    }
}