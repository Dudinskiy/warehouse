package org.example.warehouse.dao;

import lombok.AllArgsConstructor;
import org.example.warehouse.entities.CountriesEntity;
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

    @Override
    public boolean createCountry(String countryName) {
        String query = "insert into lab2_da_countries (countryName) values(?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, countryName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
