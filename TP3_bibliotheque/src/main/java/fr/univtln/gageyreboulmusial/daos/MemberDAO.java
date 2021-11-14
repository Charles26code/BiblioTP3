package fr.univtln.gageyreboulmusial.daos;

import fr.univtln.gageyreboulmusial.entities.Member;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

@Log
public class MemberDAO extends AbstractDAO<Member> {
    public MemberDAO() {
        super(
                "INSERT INTO MEMBER (name,first_name,status,idlibrary) VALUES (?,?,?,?)", "UPDATE MEMBER SET name=?, first_name=?, status=?, idlibrary=? WHERE id=?"
        );
    }

    @Override
    protected Member fromResultSet(ResultSet resultSet) throws SQLException {
        return Member.memberBuilder()
                .id(resultSet.getInt("id"))
                .first_name(resultSet.getString("first_name"))
                .name(resultSet.getString("name"))
                .status(Enum.valueOf(
                        Member.Status.class,
                        resultSet.getString("status")
                ))
                .idLibrary(resultSet.getInt("idLibrary"))
                .build();
    }

    @Override
    public Member persist(Member member) throws DataAccessException {
        return persist(member.getFirst_name(), member.getName(), member.getStatus(), member.getIdLibrary());
    }

    public Member persist(final String first_name, final String name, final Member.Status status, final int idLibrary) throws DataAccessException {
        try {
            persistPS.setString(1, name);
            persistPS.setString(2, first_name);
            persistPS.setString(3, status.toString());
            persistPS.setInt(4, idLibrary);
        } catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
        return super.persist();
    }

    @Override
    public void update(Member member) throws DataAccessException {
        try {
            updatePS.setString(1, member.getName());
            updatePS.setString(2, member.getFirst_name());
            updatePS.setString(3, member.getStatus().toString());
            updatePS.setInt(4, member.getIdLibrary());
            updatePS.setInt(5, member.getId());
        }
        catch (SQLException throwables) {
            throw new DataAccessException(throwables.getLocalizedMessage());
        }
    }

    @Override
    public String getTableName() {
        return "MEMBER";
    }
}
