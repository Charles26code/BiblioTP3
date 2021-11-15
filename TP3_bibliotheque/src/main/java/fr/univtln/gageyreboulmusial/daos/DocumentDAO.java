package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Document;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import javax.ws.rs.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class DocumentDAO extends AbstractDAO<Document> {
    public DocumentDAO() {
        super(
                "INSERT INTO DOCUMENT(title, idlibrary) VALUES (?,?)",
                "UPDATE DOCUMENT SET title=?, idlibrary=? WHERE id=?"
        );
    }

    @Override
    protected Document fromResultSet(ResultSet resultSet) throws SQLException {
        return Document.documentBuilder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .idlibrary(resultSet.getInt("idlibrary"))
                .build();
    }

    @Override
    public Document persist(Document document) throws DataAccessException {
        return persist(document.getTitle(), document.getIdlibrary());
    }

    @POST
    public Document persist(@QueryParam("title") final String title, @QueryParam("idlibrary") final int idlibrary) throws DataAccessException {
        try {
            persistPS.setString(1, title);
            persistPS.setInt(2, idlibrary);
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(Document document) throws DataAccessException {
        try {
            updatePS.setString(1, document.getTitle());
            updatePS.setInt(2, document.getIdlibrary());
            updatePS.setInt(3, document.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();
    }

    public void update(final String title, final int idlibrary, final int id) throws DataAccessException {
        Document d1 = Document.documentBuilder()
                .id(id)
                .title(title)
                .idlibrary(idlibrary)
                .build();
        this.update(d1);
    }

    @Override
    public String getTableName() {
        return "DOCUMENT";
    }
}