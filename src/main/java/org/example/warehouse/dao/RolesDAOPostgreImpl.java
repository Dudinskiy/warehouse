package org.example.warehouse.dao;

import lombok.AllArgsConstructor;
import org.example.warehouse.entities.RolesEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class RolesDAOPostgreImpl implements RolesDAO {
    private final DataSource dataSource;

    @Override
    public RolesEntity getRoleByName(String roleName) {
        String query = "Select * from lab2_da_roles where roleName=?;";
        ResultSet resultSet = null;
        RolesEntity role = null;

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, roleName);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int roleId = resultSet.getInt("roleId");
                String roleNameRes = resultSet.getString("roleName");

                role = new RolesEntity(1, roleId, roleNameRes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultSet);
        }
        return role;
    }

    @Override
    public List<RolesEntity> getAllRoles() {
        String query = "Select " +
                "row_number() over(order by roleName) as rowNumber, " +
                "roleId, " +
                "roleName from lab2_da_roles;";

        List<RolesEntity> roleList = new ArrayList<>();

        try (Statement statement = dataSource.getConnection()
                .createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int roleId = resultSet.getInt("roleId");
                String roleName = resultSet.getString("roleName");

                roleList.add(new RolesEntity(rowNumber, roleId, roleName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }
}
