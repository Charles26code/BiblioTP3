package fr.univtln.gageyreboulmusial.entities;

import lombok.*;
import lombok.extern.java.Log;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Log
public class Volume extends Document implements Entity {
    @NotNull(message = "A volume to refere to a document")
    private int iddocument;

    @NotEmpty(message = "A volume need to have an author")
    private String author;

    @Builder(builderMethodName = "volumeBuilder")
    public Volume(int id, String title, String author, int iddocument, int idlibrary) {
        super(id, title, idlibrary);
        this.author = author;
        this.iddocument = iddocument;
    }
}