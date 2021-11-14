package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Material;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class MaterialDAO extends AbstractDAO<Material> {
    public MaterialDAO() {
        super(
                "INSERT INTO MATERIAL(outOfOrder, idLibrary) VALUES (?)",
                "UPDATE MATERIAL SET outOfOrder=?,idlibrary=? WHERE id=?"
        );
    }


    @Override
    protected Material fromResultSet(ResultSet resultSet) throws SQLException {
        return Material.materialBuilder()
                .id(resultSet.getInt("id"))
                .outOfOrder(resultSet.getBoolean("outOfOrder"))
                .idlibrary(resultSet.getInt("idlibrary"))
                .build();
    }

    @Override
    public Material persist(Material material) throws DataAccessException {
        return persist(material.isOutOfOrder(), material.getIdlibrary());
    }

    public Material persist(final Boolean outOfOrder, final int idlibrary) throws DataAccessException {
        try {
            persistPS.setBoolean(1, outOfOrder);
            persistPS.setInt(2, idlibrary);
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(Material material) throws DataAccessException {
        try {
            updatePS.setBoolean(1, material.isOutOfOrder());
            updatePS.setInt(2, material.getIdlibrary());
            updatePS.setInt(3, material.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();
    }

    public void update(final Boolean outOfOrder, final int idlibrary, final int id) throws DataAccessException {
        Material m1 = Material.materialBuilder()
                .id(id)
                .outOfOrder(outOfOrder)
                .idlibrary(idlibrary)
                .build();
        this.update(m1);
    }


    @Override
    public String getTableName() {
        return "MATERIAL";
    }
}
