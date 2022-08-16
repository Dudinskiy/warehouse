package org.example.warehouse.dao;

import lombok.AllArgsConstructor;
import org.example.warehouse.entities.UsersEntityFull;
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
public class UsersDAOPostgreImpl implements UsersDAO {
    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersDAOPostgreImpl.class);

    @Override
    public boolean createUser(String firstName, String lastName
            , String login, String password, int roleId) {
        String query = "insert into lab2_da_users (firstName, lastName, login, password, roleId) " +
                "values(?, ?, ?, ?, ?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setInt(5, roleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating User", e);
            return false;
        }
        return true;
    }

    @Override
    public UsersEntityFull getUserByLogin(String login) {
        System.out.println("DAO input:" + login);

        UsersEntityFull entity = null;
        ResultSet resultSet = null;
        String query = "select " +
                "u.userid as userid, " +
                "u.firstname as firstname, " +
                "u.lastname as lastname, " +
                "u.login as login, " +
                "u.password as password, " +
                "r.rolename as rolename " +
                "from lab2_da_users u " +
                "join lab2_da_roles r on u.roleid= r.roleid " +
                "where u.login=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, login);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String loginRes = resultSet.getString("login");
                String password = resultSet.getString("password");
                String role = resultSet.getString("roleName");

                entity = new UsersEntityFull(1, userId, firstName, lastName
                        , loginRes, password, role);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting User by login", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return entity;
    }

    @Override
    public List<UsersEntityFull> getAllUsers() {
        List<UsersEntityFull> entityList = new ArrayList<>();
        String query = "select " +
                "row_number() over(order by lastname) as rowNumber," +
                "u.userid as userid, " +
                "u.firstname as firstname, " +
                "u.lastname as lastname, " +
                "u.login as login, " +
                "u.password as password, " +
                "r.rolename as rolename " +
                "from lab2_da_users u " +
                "join lab2_da_roles r on u.roleid= r.roleid;";

        try (Statement statement = dataSource.getConnection()
                .createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String loginRes = resultSet.getString("login");
                String password = resultSet.getString("password");
                String role = resultSet.getString("roleName");

                entityList.add(new UsersEntityFull(rowNumber, userId, firstName
                        , lastName, loginRes, password, role));
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all Users", e);
        }
        return entityList;
    }

    @Override
    public boolean editUser(String login) {
        return false;
    }

    @Override
    public boolean deleteUserByLogin(String login) {
        String query = "delete from lab2_da_users where login=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {
            statement.setString(1, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting User by login", e);
            return false;
        }
        return true;
    }
}
