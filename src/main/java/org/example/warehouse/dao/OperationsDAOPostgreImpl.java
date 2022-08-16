package org.example.warehouse.dao;

import org.example.warehouse.entities.OperationsEntity;
import org.example.warehouse.entities.OperationsEntityFull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OperationsDAOPostgreImpl implements OperationsDAO {
    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationsDAOPostgreImpl.class);

    public OperationsDAOPostgreImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createOperation(int typeId, String invoice) {
        String query = "insert into lab2_da_operations (operationDate, typeId, invoiceNumber) values(?, ?, ?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setObject(1, LocalDateTime.now());
            statement.setInt(2, typeId);
            statement.setString(3, invoice);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating Operation",e);
            return false;
        }
        return true;
    }

    @Override
    public OperationsEntity getOperationById(int id) {
        OperationsEntity operation = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_operations where operationId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int operationId = resultSet.getInt("operationId");
                LocalDateTime operationDate = resultSet
                        .getObject("operationDate", LocalDateTime.class);
                int typeId = resultSet.getInt("typeId");
                String invoiceNumber = resultSet.getString("invoiceNumber");
                int userId = resultSet.getInt("userId");

                operation = new OperationsEntity(1, operationId
                        , operationDate, typeId, invoiceNumber, userId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Operation by id", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return operation;
    }

    @Override
    public OperationsEntity getOperationByInvoice(String invoice) {
        OperationsEntity operation = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_operations where invoiceNumber=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, invoice);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int operationId = resultSet.getInt("operationId");
                LocalDateTime operationDate = resultSet
                        .getObject("operationDate", LocalDateTime.class);
                int typeId = resultSet.getInt("typeId");
                String invoiceNumber = resultSet.getString("invoiceNumber");
                int userId = resultSet.getInt("userId");

                operation = new OperationsEntity(1, operationId
                        , operationDate, typeId, invoiceNumber, userId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Operation by invoice", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return operation;
    }

    @Override
    public List<OperationsEntityFull> getOperationByInvoiceFull(String invoice) {
        List<OperationsEntityFull> entityList = new ArrayList<>();
        ResultSet resultSet = null;
        String query = "select op.operationid as operationid, " +
                "op.operationdate as operationdate, " +
                "ot.typename as typename, " +
                "op.invoicenumber as invoicenumber, " +
                "prt.productid as productid, " +
                "prt.productname as productname, " +
                "prt.price as price," +
                "oi.productamount as productamount, " +
                "prc.producername as producername, " +
                "cnt.countryname as countryname, " +
                "op.userid as userid, " +
                "usr.firstname as firstname, " +
                "usr.lastname as lastname " +
                "from lab2_da_operations op " +
                "join lab2_da_operationtype ot on op.typeid=ot.typeid " +
                "join lab2_da_operationinfo oi on op.operationid=oi.operationid " +
                "join lab2_da_products prt on oi.productid=prt.productid " +
                "join lab2_da_producers prc on prt.producerid=prc.producerid " +
                "join lab2_da_countries cnt on prc.countryid=cnt.countryid " +
                "join lab2_da_users usr on op.userid=usr.userid " +
                "where op.invoicenumber=? " +
                "order by prt.productname;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, invoice);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int operationId = resultSet.getInt("operationId");
                LocalDateTime operationDate = resultSet
                        .getObject("operationDate", LocalDateTime.class);
                String typeName = resultSet.getString("typeName");
                String invoiceNumber = resultSet.getString("invoiceNumber");
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                OperationsEntityFull operation = new OperationsEntityFull(1, operationId,
                        operationDate, typeName, invoiceNumber, productId,
                        productName, price, productAmount, producerName
                        , countryName, userId, firstName, lastName);

                entityList.add(operation);
            }
        } catch (SQLException e) {
           LOGGER.error("Error getting OperationFull by invoice", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return entityList;
    }

    @Override
    public List<OperationsEntityFull> getAllOperationByPeriodFull(LocalDate start, LocalDate end) {
        List<OperationsEntityFull> entityList = new ArrayList<>();
        ResultSet resultSet = null;
        String query = "Select " +
                "row_number() over (order by op.operationdate) as rowNumber" +
                "op.operationid as operationid, " +
                "date_trunc('second', op.operationdate) as operationdate, " +
                "ot.typename as typename, " +
                "op.invoicenumber, " +
                "prt.productid as productid, " +
                "prt.productname as productname, " +
                "oi.productamount as productamount, " +
                "prc.producername as producername, " +
                "prt.price as price," +
                "cnt.countryname as countryname " +
                "op.userid as userid, " +
                "usr.firstname as firstname, " +
                "usr.lastname as lastname " +
                "from lab2_da_operations op " +
                "join lab2_da_operationtype ot on op.typeid=ot.typeid " +
                "join lab2_da_operationinfo oi on op.operationid=oi.operationid " +
                "join lab2_da_products prt on oi.productid=prt.productid " +
                "join lab2_da_producers prc on prt.producerid=prc.producerid " +
                "join lab2_da_countries cnt on prc.countryid=cnt.countryid " +
                "join lab2_da_users usr on op.userid=usr.userid " +
                "where date_trunc('day', operationdate)>=? " +
                "and date_trunc('day', operationdate)<=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setObject(1, start);
            statement.setObject(2, end);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int operationId = resultSet.getInt("operationId");
                LocalDateTime operationDate = resultSet
                        .getObject("operationDate", LocalDateTime.class);
                String typeName = resultSet.getString("typeName");
                String invoiceNumber = resultSet.getString("invoiceNumber");
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                OperationsEntityFull operation = new OperationsEntityFull(rowNumber, operationId,
                        operationDate, typeName, invoiceNumber, productId,
                        productName, price, productAmount, producerName
                        , countryName, userId, firstName, lastName);

                entityList.add(operation);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all OperationFull by period", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return entityList;
    }

    @Override
    public List<OperationsEntityFull> getAllOperationByPeriodAndTypeFull(LocalDate start
            , LocalDate end, String type) {
        List<OperationsEntityFull> entityList = new ArrayList<>();
        ResultSet resultSet = null;
        String query = "Select " +
                "row_number() over (order by op.operationdate;) as rowNumber" +
                "op.operationid as operationid, " +
                "date_trunc('second', op.operationdate) as operationdate, " +
                "ot.typename as typename, " +
                "op.invoicenumber, " +
                "prt.productid as productid, " +
                "prt.productname as productname, " +
                "prt.price as price," +
                "oi.productamount as productamount, " +
                "prc.producername as producername, " +
                "cnt.countryname as countryname " +
                "op.userid as userid, " +
                "usr.firstname as firstname, " +
                "usr.lastname as lastname " +
                "from lab2_da_operations op " +
                "join lab2_da_operationtype ot on op.typeid=ot.typeid " +
                "join lab2_da_operationinfo oi on op.operationid=oi.operationid " +
                "join lab2_da_products prt on oi.productid=prt.productid " +
                "join lab2_da_producers prc on prt.producerid=prc.producerid " +
                "join lab2_da_countries cnt on prc.countryid=cnt.countryid " +
                "join lab2_da_users usr on op.userid=usr.userid " +
                "where date_trunc('day', operationdate)>=? " +
                "and date_trunc('day', operationdate)<=?  " +
                "and ot.typename=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setObject(1, start);
            statement.setObject(2, end);
            statement.setString(3, type);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int operationId = resultSet.getInt("operationId");
                LocalDateTime operationDate = resultSet
                        .getObject("operationDate", LocalDateTime.class);
                String typeName = resultSet.getString("typeName");
                String invoiceNumber = resultSet.getString("invoiceNumber");
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");
                int userId = resultSet.getInt("userId");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                OperationsEntityFull operation = new OperationsEntityFull(rowNumber, operationId,
                        operationDate, typeName, invoiceNumber, productId,
                        productName, price, productAmount, producerName
                        , countryName, userId, firstName, lastName);

                entityList.add(operation);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting OperationFull by period and type", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return entityList;
    }


    @Override
    public boolean deleteOperationById(int id) {
        String query = "delete from lab2_da_operations where operationId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting Operation by id", e);
            return false;
        }
        return true;
    }
}
