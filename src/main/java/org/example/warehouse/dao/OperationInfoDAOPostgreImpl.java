package org.example.warehouse.dao;

import org.example.warehouse.entities.OperationInfoEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class OperationInfoDAOPostgreImpl implements OperationInfoDAO {
    private final DataSource dataSource;

    public OperationInfoDAOPostgreImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createOperationInfo(int operationId, int productId, int productAmount) {
        String query = "insert into lab2_da_operationInfo (operationId, productId," +
                " productAmount) values(?, ?, ?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, operationId);
            statement.setInt(2, productId);
            statement.setInt(3, productAmount);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public OperationInfoEntity getOperationInfoByOperationId(int id) {
        OperationInfoEntity operationInfo = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_operationInfo where operationId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int operationId = resultSet.getInt("operationId");
                int productId = resultSet.getInt("productId");
                int productAmount = resultSet.getInt("productAmount");

                operationInfo = new OperationInfoEntity(operationId, productId, productAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultSet);
        }
        return operationInfo;
    }

    @Override
    public OperationInfoEntity getOperationInfoByProductId(int id) {
        OperationInfoEntity operationInfo = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_operationInfo where productId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int operationId = resultSet.getInt("operationId");
                int productId = resultSet.getInt("productId");
                int productAmount = resultSet.getInt("productAmount");

                operationInfo = new OperationInfoEntity(operationId, productId, productAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultSet);
        }
        return operationInfo;
    }

    @Override
    public OperationInfoEntity getOperationInfoByOperationIdAndProductId(int operId, int prodId) {
        OperationInfoEntity operationInfo = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_operationInfo where operationId=? and productId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, operId);
            statement.setInt(2, prodId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int operationId = resultSet.getInt("operationId");
                int productId = resultSet.getInt("productId");
                int productAmount = resultSet.getInt("productAmount");

                operationInfo = new OperationInfoEntity(operationId, productId, productAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           JdbcUtil.free(resultSet);
        }
        return operationInfo;
    }

    @Override
    public boolean deleteOperationInfoByOperationId(int id) {
        String query = "delete from lab2_da_operationInfo where operationId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
