package org.example.warehouse.dao;

import lombok.AllArgsConstructor;
import org.example.warehouse.entities.CountriesEntity;
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
public class CountriesDAOPostgreImpl implements CountriesDAO {
    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(CountriesDAOPostgreImpl.class);

    @Override
    public boolean createCountry(String countryName) {
        String query = "insert into lab2_da_countries (countryName) values(?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, countryName);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error creating Country", e);
            return false;
        }
        return true;
    }

    @Override
    public CountriesEntity getCountryById(int id) {
        CountriesEntity country = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_countries where countryId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int countryId = resultSet.getInt("countryId");
                String countryName = resultSet.getString("countryName");

                country = new CountriesEntity(1, countryId, countryName);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting Country by id", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return country;
    }

    @Override
    public CountriesEntity getCountryByName(String name) {
        CountriesEntity country = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_countries where countryName=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int countryId = resultSet.getInt("countryId");
                String countryName = resultSet.getString("countryName");

                country = new CountriesEntity(1, countryId, countryName);
            }

        } catch (SQLException e) {
            LOGGER.error("Error getting Country by name", e);
        } finally {
            JdbcUtil.free(resultSet);
        }
        return country;
    }

    @Override
    public List<CountriesEntity> getAllCountries() {
        List<CountriesEntity> countriesEntityList = new ArrayList<>();
        String query = "Select " +
                "row_number() over(order by countryName) as rowNumber, " +
                "countryId, " +
                "countryName " +
                "from lab2_da_countries;";

        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int countryId = resultSet.getInt("countryId");
                String countryName = resultSet.getString("countryName");

                CountriesEntity countriesEntity = new CountriesEntity(rowNumber, countryId, countryName);

                countriesEntityList.add(countriesEntity);
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting all Countries", e);
        }
        return countriesEntityList;
    }

    @Override
    public boolean deleteCountryById(int id) {
        String query = "delete from lab2_da_countries where countryId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error deleting Country by id", e);
            return false;
        }
        return true;
    }
}
