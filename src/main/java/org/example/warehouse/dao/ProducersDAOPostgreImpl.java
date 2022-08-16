package org.example.warehouse.dao;

import org.example.warehouse.entities.ProducersEntity;
import org.example.warehouse.entities.ProducersEntityFull;
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
public class ProducersDAOPostgreImpl implements ProducersDAO {
    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducersDAOPostgreImpl.class);

    public ProducersDAOPostgreImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createProducer(String producerName, int countryId) {
        String query = "insert into lab2_da_producers (producerName, countryId) values(?, ?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, producerName);
            statement.setInt(2, countryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating Producer", e);
            return false;
        }
        return true;
    }

    @Override
    public ProducersEntity getProducerById(int id) {
        ProducersEntity producer = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_producers where producerId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int producerId = resultSet.getInt("producerId");
                String producerName = resultSet.getString("producerName");
                int countryId = resultSet.getInt("countryId");

                producer = new ProducersEntity(1, producerId, producerName, countryId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Producer by id", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return producer;
    }

    @Override
    public ProducersEntity getProducerByName(String name) {
        ProducersEntity producer = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_producers where producerName=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int producerId = resultSet.getInt("producerId");
                String producerName = resultSet.getString("producerName");
                int countryId = resultSet.getInt("countryId");

                producer = new ProducersEntity(1, producerId, producerName, countryId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Producer by name", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return producer;
    }

    @Override
    public ProducersEntityFull getProducerByNameFull(String name) {
        ProducersEntityFull producer = null;
        ResultSet resultSet = null;
        String query = "Select " +
                "pr.producerid as producerid, " +
                "pr.producername as producername, " +
                "cn.countryname as countryname " +
                "from lab2_da_producers pr " +
                "join lab2_da_countries cn on pr.countryid=cn.countryid " +
                "where pr.producername=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int producerId = resultSet.getInt("producerId");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");

                producer = new ProducersEntityFull(1, producerId
                        , producerName, countryName);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting ProducerFull by name", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return producer;
    }

    @Override
    public List<ProducersEntityFull> getAllProducerFull() {
        List<ProducersEntityFull> producerList = new ArrayList<>();
        String query = "Select " +
                "row_number() over(order by pr.producername) as rowNumber," +
                "pr.producerid as producerid, " +
                "pr.producername as producername, " +
                "cn.countryname as countryname " +
                "from lab2_da_producers pr " +
                "join lab2_da_countries cn on pr.countryid=cn.countryid;";

        try (Statement statement = dataSource.getConnection()
                .createStatement();
             ResultSet resultSet = statement.executeQuery(query);) {

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int producerId = resultSet.getInt("producerId");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");

                ProducersEntityFull producer = new ProducersEntityFull(rowNumber, producerId
                        , producerName, countryName);
                producerList.add(producer);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all ProducerFull", e);
        }
        return producerList;
    }

    @Override
    public boolean deleteProducerById(int id) {
        String query = "delete from lab2_da_producers where producerId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting Producer by id", e);
            return false;
        }
        return true;
    }
}
