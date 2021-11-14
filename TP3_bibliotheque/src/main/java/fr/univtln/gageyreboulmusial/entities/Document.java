package fr.univtln.gageyreboulmusial.entities;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder(builderMethodName = "documentBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Document implements Entity {
    @NotNull(message = "Entity's id can't be null")
    private int id = -1;

    @NotEmpty(message = "A document must have a title")
    private String title;

    @NotNull(message = "A document is affected on a valid library")
    private int idlibrary;
}

