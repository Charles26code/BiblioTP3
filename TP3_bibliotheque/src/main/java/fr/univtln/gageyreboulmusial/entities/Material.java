package fr.univtln.gageyreboulmusial.entities;

import lombok.*;
import lombok.extern.java.Log;

import javax.validation.constraints.NotNull;

@Data
@Builder(builderMethodName = "materialBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Log
public class Material implements Entity {

    @NotNull
    private int id = -1;

    private boolean outOfOrder;

    @NotNull
    private int idlibrary;
}