package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Document;
import fr.univtln.gageyreboulmusial.entities.Volume;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class VolumeDAO extends AbstractDAO<Volume>{

    public VolumeDAO() {
        super("INSERT INTO Volume(author, iddocument) VALUES (?,?)",
                "UPDATE VOLUME SET author=?, iddocument=? WHERE id=?");
    }

    @Override
    public String getTableName() {
        return "VOLUME";
    }

    @Override
    protected Volume fromResultSet(ResultSet resultSet) throws SQLException {
        Volume volume = null;
        try (DocumentDAO documentDAO = new DocumentDAO()){
            Optional<Document> document = documentDAO.find(resultSet.getInt("iddocument"));
            volume = Volume.volumeBuilder()
                    .id(resultSet.getInt("id"))
                    .author(resultSet.getString("author"))
                    .iddocument(resultSet.getInt("iddocument"))
                    .title(document.get().getTitle())
                    .idlibrary(document.get().getIdlibrary())
                    .build();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        return volume;
    }

    @Override
    public Volume persist(Volume volume) throws DataAccessException {
        return persist(volume.getAuthor(), volume.getTitle(), volume.getIdlibrary());
    }

    public Volume persist(final String author, final String title, final int idlibrary) throws DataAccessException {
        try {
            DocumentDAO documentDAO = new DocumentDAO();
            Document d1 = documentDAO.persist(title, idlibrary);
            persistPS.setString(1, author);
            persistPS.setInt(2, d1.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(Volume volume) throws DataAccessException {
        try {
            DocumentDAO documentDAO = new DocumentDAO();
            documentDAO.update(volume.getTitle(), volume.getIdlibrary(), volume.getIddocument());
            updatePS.setString(1, volume.getAuthor());
            updatePS.setInt(2, volume.getIddocument());
            updatePS.setInt(3, volume.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();
    }
}
