package fr.univtln.gageyreboulmusial.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Log
public class ComicBook extends Volume implements Entity {
    @NotNull
    private String designer;

    @NotNull(message = "A Comicbook must reference a volume")
    private int idvolume;

    @Builder(builderMethodName = "comicbookBuilder")
    public ComicBook(int id, String title, String author, int iddocument, String designer, int idvolume, int idlibrary) {
        super(id, title, author, iddocument, idlibrary);
        this.designer = designer;
        this.idvolume = idvolume;
    }
}