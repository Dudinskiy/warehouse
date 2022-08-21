package org.example.warehouse.dao;

import lombok.AllArgsConstructor;
import org.example.warehouse.entities.RolesEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesDAOPostgreImpl.class);

    @Override
    public RolesEntity getRoleByName(String role) {
        String query = "Select * from lab2_da_roles where role=?;";
        ResultSet resultSet = null;
        RolesEntity roleEntity = null;

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, role);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int roleId = resultSet.getInt("roleId");
                String roleNameRes = resultSet.getString("roleName");
                String roleRes = resultSet.getString("role");

                roleEntity = new RolesEntity(1, roleId, roleNameRes, roleRes);
            }
        } catch (SQLException e) {
           LOGGER.error("Error getting Role by name", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return roleEntity;
    }

    @Override
    public List<RolesEntity> getAllRoles() {
        String query = "Select " +
                "row_number() over(order by roleName) as rowNumber, " +
                "roleId, " +
                "roleName, " +
                "role " +
                "from lab2_da_roles;";

        List<RolesEntity> roleList = new ArrayList<>();

        try (Statement statement = dataSource.getConnection()
                .createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int roleId = resultSet.getInt("roleId");
                String roleName = resultSet.getString("roleName");
                String roleRes = resultSet.getString("role");

                roleList.add(new RolesEntity(rowNumber, roleId, roleName, roleRes));
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all Roles", e);
        }
        return roleList;
    }
}
