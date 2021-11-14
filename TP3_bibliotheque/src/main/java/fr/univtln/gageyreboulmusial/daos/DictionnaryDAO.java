package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Dictionnary;
import fr.univtln.gageyreboulmusial.entities.Volume;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class DictionnaryDAO extends AbstractDAO<Dictionnary>{

    public DictionnaryDAO() {
        super("INSERT INTO Dictionnary(theme, idvolume) VALUES (?,?)",
                "UPDATE Dictionnary SET theme=?, idvolume=? WHERE id=?");
    }

    @Override
    public String getTableName() {
        return "DICTIONNARY";
    }

    @Override
    protected Dictionnary fromResultSet(ResultSet resultSet) throws SQLException {
        Dictionnary dictionnary = null;
        try (VolumeDAO volumeDAO = new VolumeDAO()) {
            Optional<Volume> volume = volumeDAO.find(resultSet.getInt("idvolume"));
            dictionnary = Dictionnary.dictionnaryBuilder()
                    .id(resultSet.getInt("id"))
                    .theme(resultSet.getString("theme"))
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
        return dictionnary;
    }

    @Override
    public Dictionnary persist(Dictionnary dictionnary) throws DataAccessException {
        return persist(dictionnary.getTheme(), dictionnary.getAuthor(), dictionnary.getTitle(), dictionnary.getIdlibrary());
    }

    public Dictionnary persist(final String theme, final String author,  final String title, final int idlibrary) throws DataAccessException {
        try {
            VolumeDAO volumeDAO = new VolumeDAO();
            Volume v1 = volumeDAO.persist(author, title, idlibrary);
            persistPS.setString(1, theme);
            persistPS.setInt(2, v1.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }



    @Override
    public void update(Dictionnary dictionnary) throws DataAccessException {
        try {
            VolumeDAO volumeDAO = new VolumeDAO();
            Volume v1 = Volume.volumeBuilder()
                    .title(dictionnary.getTitle())
                    .id(dictionnary.getIdvolume())
                    .author(dictionnary.getAuthor())
                    .idlibrary(dictionnary.getIdlibrary())
                    .iddocument(dictionnary.getIddocument())
                    .build();
            volumeDAO.update(v1);
            updatePS.setString(1, dictionnary.getAuthor());
            updatePS.setInt(2, dictionnary.getIdvolume());
            updatePS.setInt(3, dictionnary.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();
    }
}
