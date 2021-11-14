package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Library;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class LibraryDAO extends AbstractDAO<Library> {
    public LibraryDAO() {
        super(
                "INSERT INTO library DEFAULT VALUES;",
                ""
        );
    }

    @Override
    protected Library fromResultSet(ResultSet resultSet) throws SQLException {
        return Library.libraryBuilder()
                .id(resultSet.getInt("ID"))
                .build();
    }

    @Override
    public Library persist(Library library) throws DataAccessException {
        return persist();
    }

    @Override
    public Library persist() throws DataAccessException {
        return super.persist();
    }

    @Override
    public void update(Library library) throws DataAccessException {
        super.update();
    }

    @Override
    public String getTableName() {
        return "LIBRARY";
    }
}
