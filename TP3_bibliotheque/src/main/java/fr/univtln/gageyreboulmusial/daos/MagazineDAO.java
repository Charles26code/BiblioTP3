package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Document;
import fr.univtln.gageyreboulmusial.entities.Magazine;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class MagazineDAO extends AbstractDAO<Magazine>{
    public MagazineDAO() {
        super(
                "INSERT INTO MAGAZINE(number, iddocument) VALUES (?,?)",
                "UPDATE MAGAZINE SET number=?, iddocument=? WHERE id=?"
        );
    }

    @Override
    public String getTableName() {
        return "MAGAZINE";
    }

    @Override
    protected Magazine fromResultSet(ResultSet resultSet) throws SQLException {
        Magazine magazine = null;
        try (DocumentDAO documentDAO = new DocumentDAO()){
            Optional<Document> document = documentDAO.find(resultSet.getInt("iddocument"));
            magazine = Magazine.magazineBuilder()
                        .id(resultSet.getInt("id"))
                        .number(resultSet.getInt("number"))
                        .iddocument(document.get().getId())
                        .title(document.get().getTitle())
                        .idlibrary(document.get().getIdlibrary())
                        .build();

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return magazine;
    }

    @Override
    public Magazine persist(Magazine magazine) throws DataAccessException {
        return persist(magazine.getNumber(), magazine.getTitle(), magazine.getIdlibrary());
    }

    public Magazine persist(final int number, final String title, final int idlibrary) throws DataAccessException {
        try {
            DocumentDAO documentDAO = new DocumentDAO();
            Document d1 = documentDAO.persist(title, idlibrary);
            persistPS.setInt(1, number);
            persistPS.setInt(2, d1.getId());
        } catch(SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(Magazine magazine) throws DataAccessException {
        try {
            DocumentDAO documentDAO = new DocumentDAO();
            Document d1 = Document.documentBuilder()
                    .id(magazine.getIddocument())
                    .title(magazine.getTitle())
                    .idlibrary(magazine.getIdlibrary())
                    .build();
            documentDAO.update(d1);
            updatePS.setInt(1, magazine.getNumber());
            updatePS.setInt(2, magazine.getIddocument());
            updatePS.setInt(3, magazine.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }
}
