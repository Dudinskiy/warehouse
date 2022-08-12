package org.example.warehouse.dao;

import org.example.warehouse.entities.ProductsEntity;
import org.example.warehouse.entities.ProductsEntityFull;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductsDAOPostgreImpl implements ProductsDAO {
    private final DataSource dataSource;

    public ProductsDAOPostgreImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createProduct(String productName, int producerId
            , BigDecimal price) {
        String query = "insert into lab2_da_products (productName, producerId," +
                " price, productAmount) values(?, ?, ?, ?);";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, productName);
            statement.setInt(2, producerId);
            statement.setBigDecimal(3, price);
            statement.setInt(4, 0);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProductAmountById(int productId, int productAmount) {
        String query = "update lab2_da_products set productAmount=? where productId=?";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {
            statement.setInt(1, productAmount);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ProductsEntity getProductById(int id) {
        ProductsEntity product = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_products where productId=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                int producerId = resultSet.getInt("producerId");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");

                product = new ProductsEntity(1, productId, productName, producerId
                        , price, productAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultSet);
        }
        return product;
    }

    @Override
    public ProductsEntity getProductByName(String name) {
        ProductsEntity product = null;
        ResultSet resultSet = null;
        String query = "select * from lab2_da_products where productName=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                int producerId = resultSet.getInt("producerId");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");

                product = new ProductsEntity(1, productId, productName, producerId
                        , price, productAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultSet);
        }
        return product;
    }

    @Override
    public ProductsEntityFull getProductByNameFull(String name) {
        ProductsEntityFull product = null;
        ResultSet resultSet = null;
        String query = "select " +
                "prt.productid as productid," +
                "prt.productname as productname, " +
                "prc.producername as producername, " +
                "cnt.countryname as countryname, " +
                "prt.price as price, " +
                "prt.productamount as productamount " +
                "from lab2_da_products prt " +
                "join lab2_da_producers prc on prt.producerid=prc.producerid " +
                "join lab2_da_countries cnt on prc.countryid=cnt.countryid " +
                "where prt.productname=?;";

        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, name);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");

                product = new ProductsEntityFull(1, productId, productName
                        , producerName, countryName, price, productAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultSet);
        }
        return product;
    }

    @Override
    public List<ProductsEntityFull> getAllProductFull() {
        List<ProductsEntityFull> productList = new ArrayList<>();
        String query = "select " +
                "row_number() over(order by prt.productname) as rowNumber," +
                "prt.productid as productid," +
                "prt.productname as productname, " +
                "prc.producername as producername, " +
                "cnt.countryname as countryname, " +
                "prt.price as price, " +
                "prt.productamount as productamount " +
                "from lab2_da_products prt " +
                "join lab2_da_producers prc on prt.producerid=prc.producerid " +
                "join lab2_da_countries cnt on prc.countryid=cnt.countryid;";


        try (Statement statement = dataSource.getConnection()
                .createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");

                ProductsEntityFull product = new ProductsEntityFull(rowNumber, productId
                        , productName, producerName, countryName, price, productAmount);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<ProductsEntityFull> getAllProductFullByProducer(String prodName) {
        List<ProductsEntityFull> productList = new ArrayList<>();
        ResultSet resultSet = null;
        String query = "select " +
                "row_number() over(order by prt.productname) as rowNumber," +
                "prt.productid as productid," +
                "prt.productname as productname, " +
                "prc.producername as producername, " +
                "cnt.countryname as countryname, " +
                "prt.price as price, " +
                "prt.productamount as productamount " +
                "from lab2_da_products prt " +
                "join lab2_da_producers prc on prt.producerid=prc.producerid " +
                "join lab2_da_countries cnt on prc.countryid=cnt.countryid " +
                "where prc.producername=?;";


        try (PreparedStatement statement = dataSource.getConnection()
                .prepareStatement(query)) {

            statement.setString(1, prodName);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int rowNumber = resultSet.getInt("rowNumber");
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                String producerName = resultSet.getString("producerName");
                String countryName = resultSet.getString("countryName");
                BigDecimal price = resultSet.getBigDecimal("price");
                int productAmount = resultSet.getInt("productAmount");

                ProductsEntityFull product = new ProductsEntityFull(rowNumber, productId
                        , productName, producerName, countryName, price, productAmount);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.free(resultSet);
        }
        return productList;
    }

    @Override
    public boolean deleteProductById(int id) {
        String query = "delete from lab2_da_products where productId=?;";

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
