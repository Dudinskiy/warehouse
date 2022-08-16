package org.example.warehouse.dao;

import org.example.warehouse.entities.OperationTypeEntity;
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
public class OperationTypeDAOPostgreImpl implements OperationTypeDAO {
    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationTypeDAOPostgreImpl.class);

    public OperationTypeDAOPostgreImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createOperationType(String typeName) {
        String query = "insert into lab2_da_operationType (typeName) values(?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, typeName);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating OperationType", e);
            return false;
        }
        return true;
    }

    @Override
    public OperationTypeEntity getOperationTypeById(int id) {
        OperationTypeEntity operationType = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_operationType where typeId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int typeId = resultSet.getInt("typeId");
                String typeName = resultSet.getString("typeName");

                operationType = new OperationTypeEntity(1, typeId, typeName);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting OperationType by id", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return operationType;
    }

    @Override
    public OperationTypeEntity getOperationTypeByName(String name) {
        OperationTypeEntity operationType = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_operationType where typeName=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int typeId = resultSet.getInt("typeId");
                String typeName = resultSet.getString("typeName");

                operationType = new OperationTypeEntity(1, typeId, typeName);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting OperationType by name", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return operationType;
    }

    @Override
    public List<OperationTypeEntity> getAllOperationType() {
        List<OperationTypeEntity> operationTypeList = new ArrayList<>();
        String query = "select " +
                "row_number() over(order by typeName) as rowNumber, " +
                "typeId, " +
                "typeName " +
                "from lab2_da_operationType;";

        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int typeId = resultSet.getInt("typeId");
                String typeName = resultSet.getString("typeName");

                OperationTypeEntity operationType = new OperationTypeEntity(rowNumber
                        , typeId, typeName);

                operationTypeList.add(operationType);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all OperationType", e);
        }
        return operationTypeList;
    }

    @Override
    public boolean deleteOperationTypeById(int id) {
        String query = "delete from lab2_da_operationType where typeId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting OperationType by id", e);
            return false;
        }
        return true;
    }
}
