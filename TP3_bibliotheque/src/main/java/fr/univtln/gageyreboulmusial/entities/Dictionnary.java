package fr.univtln.gageyreboulmusial.entities;

import lombok.*;
import lombok.extern.java.Log;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Log
public class Dictionnary extends Volume implements Entity {
        @NotEmpty(message = "A dictionnary must have a theme")
        private String theme;

        @NotNull(message = "A dictionnary must reference a volume")
        private int idvolume;

        @Builder(builderMethodName = "dictionnaryBuilder")
        public Dictionnary(int id, String title, String author, int iddocument, int idlibrary, String theme, int idvolume) {
                super(id, title, author, iddocument, idlibrary);
                this.idvolume = idvolume;
                this.theme = theme;
        }
}