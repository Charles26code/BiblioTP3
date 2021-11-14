package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.*;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Log
public class LaptopComputerDAO extends AbstractDAO<LaptopComputer> {
    public LaptopComputerDAO() {
        super("INSERT INTO LAPTOPCOMPUTER(brand, available, os, idmaterial, idmember) VALUES (?,?,?,?,?)",
        "UPDATE LAPTOPCOMPUTER SET brand=?, available=?, os=?, idmaterial=?, idmember=? WHERE id=?");
    }

    @Override
    public String getTableName() {
        return "LAPTOPCOMPUTER";
    }

    @Override
    protected LaptopComputer fromResultSet(ResultSet resultSet) throws SQLException {
        LaptopComputer laptopComputer = null;
        try (MaterialDAO materialDAO = new MaterialDAO())
        {
            Optional<Material> material = materialDAO.find(resultSet.getInt("idmaterial"));
            laptopComputer = LaptopComputer.laptopcomputerBuilder()
                    .id(resultSet.getInt("id"))
                    .available(resultSet.getBoolean("available"))
                    .brand(resultSet.getString("brand"))
                    .os(Enum.valueOf(LaptopComputer.OS.class, resultSet.getString("os")))
                    .idmember(resultSet.getInt("idmember"))
                    .idmaterial(resultSet.getInt("idmaterial"))
                    .outOfOrder(material.get().isOutOfOrder())
                    .idlibrary(material.get().getIdlibrary())
                    .build();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        return laptopComputer;
    }

    @Override
    public LaptopComputer persist(LaptopComputer laptopComputer) throws DataAccessException {
        return persist(laptopComputer.getBrand(), laptopComputer.getOs(), laptopComputer.isOutOfOrder(), laptopComputer.getIdlibrary());
    }

    public LaptopComputer persist(final String brand, final LaptopComputer.OS os, final boolean available, final int idlibrary) throws DataAccessException {
        try {
            MaterialDAO materialDAO = new MaterialDAO();
            Material m1 = materialDAO.persist(available, idlibrary);
            persistPS.setString(1, brand);
            persistPS.setString(2, os.toString());
            persistPS.setInt(3, m1.getId());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(LaptopComputer laptopComputer) throws DataAccessException {
        try {
            MaterialDAO materialDAO = new MaterialDAO();
            materialDAO.update(laptopComputer.isOutOfOrder(), laptopComputer.getIdlibrary(), laptopComputer.getIdmaterial());
            updatePS.setBoolean(1, laptopComputer.isAvailable());
            updatePS.setString(2, laptopComputer.getOs().toString());
            updatePS.setString(3, laptopComputer.getBrand());
            updatePS.setInt(4, laptopComputer.getIdmaterial());
            updatePS.setInt (5, laptopComputer.getIdmember());
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        super.update();
    }
}
