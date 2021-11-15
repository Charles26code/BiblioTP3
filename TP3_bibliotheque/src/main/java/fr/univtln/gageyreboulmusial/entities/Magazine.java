package fr.univtln.gageyreboulmusial.entities;

import lombok.*;
import lombok.extern.java.Log;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Log
public class Magazine extends Document implements Entity {

    @NotNull(message = "Magazine's number can't be null")
    private int number;

    @NotNull
    private int iddocument;

    @Builder(builderMethodName = "magazineBuilder")
    public Magazine(int id, String title, int number, int iddocument, int idlibrary) {
        super(id, title, idlibrary);
        this.number = number;
        this.iddocument = iddocument;
    }
}