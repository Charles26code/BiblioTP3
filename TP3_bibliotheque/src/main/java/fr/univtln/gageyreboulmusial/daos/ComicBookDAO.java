package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.ComicBook;
import fr.univtln.gageyreboulmusial.entities.Volume;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class ComicBookDAO extends AbstractDAO<ComicBook>{

    public ComicBookDAO() {
        super("INSERT INTO ComicBook(designer, idvolume) VALUES (?,?)",
                "UPDATE ComicBook SET designer=?, idvolume=? WHERE id=?");
    }

    @Override
    public String getTableName() {
        return "COMICBOOK";
    }

    @Override
    protected ComicBook fromResultSet(ResultSet resultSet) throws SQLException {
        ComicBook comicBook = null;
        try (VolumeDAO volumeDAO = new VolumeDAO()) {
            Optional<Volume> volume = volumeDAO.find(resultSet.getInt("idvolume"));
            comicBook = ComicBook.comicbookBuilder()
                    .id(resultSet.getInt("id"))
                    .designer(resultSet.getString("designer"))
                    .idvolume(resultSet.getInt("idvolume"))
                    .author(volume.get().getAuthor())
                    .title(volume.get().getTitle())
                    .idlibrary(volume.get().getIdlibrary())
                    .build();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        return comicBook;
    }

    @Override
    public ComicBook persist(ComicBook comicBook) throws DataAccessException {
        return persist(comicBook.getDesigner(), comicBook.getAuthor(), comicBook.getTitle(), comicBook.getIdlibrary());
    }

    public ComicBook persist(final String designer, final String author, final String title, final int idlibrary) throws DataAccessException {
        try {
            VolumeDAO volumeDAO = new VolumeDAO();
            Volume v1 = volumeDAO.persist(author, title, idlibrary);
            persistPS.setString(1, designer);
            persistPS.setInt(2, v1.getId());
        }
        catch(SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(ComicBook comicBook) throws DataAccessException {
        try {
            VolumeDAO volumeDAO = new VolumeDAO();
            Volume v1 = Volume.volumeBuilder()
                    .title(comicBook.getTitle())
                    .id(comicBook.getIdvolume())
                    .author(comicBook.getAuthor())
                    .idlibrary(comicBook.getIdlibrary())
                    .iddocument(comicBook.getIddocument())
                    .build();
            volumeDAO.update(v1);
            updatePS.setString(1, comicBook.getAuthor());
            updatePS.setInt(2, comicBook.getIdvolume());
            updatePS.setInt(3, comicBook.getId());
        }
        catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();
    }
}
