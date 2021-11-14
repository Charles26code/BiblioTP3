package fr.univtln.gageyreboulmusial.entities;

import fr.univtln.gageyreboulmusial.Borrowable;
import lombok.*;
import lombok.extern.java.Log;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Log
public class LaptopComputer extends Material implements Borrowable, Entity {
    @NotEmpty
    private String brand;

    @NotEmpty
    private OS os;
    private boolean available = true;
    private int idmaterial;
    private int idmember;

    @Builder(builderMethodName = "laptopcomputerBuilder")
    public LaptopComputer(int id, String brand, OS os, boolean available, int idmaterial, int idmember, boolean outOfOrder, int idlibrary) {
        super(id, outOfOrder, idlibrary);
        this.brand = brand;
        this.os = os;
        this.available = available;
        this.idmember = idmember;
        this.idmaterial  = idmaterial;
    }

    public enum OS{
        LINUX, WINDOWS
    }

    /**
     * This function is used to find out if a laptop is available
     * Same as in Book Class
     * @return
     */
    @Override
    public boolean isAvailable() {
        return this.available;
    }
}