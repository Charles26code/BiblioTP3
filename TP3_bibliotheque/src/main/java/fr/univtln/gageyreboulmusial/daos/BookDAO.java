package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Book;
import fr.univtln.gageyreboulmusial.entities.Volume;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class BookDAO extends AbstractDAO<Book>{

    public BookDAO() {
        super("INSERT INTO BOOK(available, idvolume) VALUES (?,?)",
                "UPDATE BOOK SET available=?, idvolume=? WHERE id=?");
    }

    @Override
    public String getTableName() {
        return "BOOK";
    }


    @Override
    protected Book fromResultSet(ResultSet resultSet) throws SQLException {
        Book book = null;
        try (VolumeDAO volumeDAO = new VolumeDAO()) {
            Optional<Volume> volume = volumeDAO.find(resultSet.getInt("idvolume"));
            book = Book.bookBuilder()
                    .id(resultSet.getInt("id"))
                    .available(resultSet.getBoolean("available"))
                    .idvolume(resultSet.getInt("idvolume"))
                    .author(volume.get().getAuthor())
                    .title(volume.get().getTitle())
                    .idlibrary(volume.get().getIdlibrary())
                    .iddocument(volume.get().getIddocument())
                    .build();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public Book persist(Book book) throws DataAccessException {
        return persist(book.isAvailable(), book.getAuthor(), book.getTitle(), book.getIdlibrary());
    }

    public Book persist(final Boolean available, final String author,  final String title, final int idlibrary) throws DataAccessException {
        try {
            VolumeDAO volumeDAO = new VolumeDAO();
            Volume v1 = volumeDAO.persist(author, title, idlibrary);
            persistPS.setBoolean(1, available);
            persistPS.setInt(2, v1.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }


    @Override
    public void update(Book book) throws DataAccessException {
        try {
            VolumeDAO volumeDAO = new VolumeDAO();
            Volume v1 = Volume.volumeBuilder()
                    .title(book.getTitle())
                    .id(book.getIdvolume())
                    .author(book.getAuthor())
                    .idlibrary(book.getIdlibrary())
                    .iddocument(book.getIddocument())
                    .build();
            volumeDAO.update(v1);
            updatePS.setString(1, book.getAuthor());
            updatePS.setInt(2, book.getIdvolume());
            updatePS.setInt(3, book.getId());
        }
        catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();
    }


}
