package fr.univtln.gageyreboulmusial.entities;

import fr.univtln.gageyreboulmusial.Borrowable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Log
public class Book extends Volume implements Borrowable, Entity {
    private boolean available = true;

    @NotNull(message = "A book must reference a volume")
    private int idvolume;

    @Builder(builderMethodName = "bookBuilder")
    public Book(int id, String title, String author, int iddocument, int idvolume, boolean available, int idlibrary) {
        super(id, title, author, iddocument, idlibrary);
        this.available = available;
        this.idvolume = idvolume;
    }

    /**
     * This function is used to find out if a book is available
     * @return a boolean
     */
    @Override
    public boolean isAvailable() {
        return this.available;
    }
}