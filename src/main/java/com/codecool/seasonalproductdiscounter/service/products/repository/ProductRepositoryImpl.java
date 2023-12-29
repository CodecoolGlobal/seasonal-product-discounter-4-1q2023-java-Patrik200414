package com.codecool.seasonalproductdiscounter.service.products.repository;

import com.codecool.seasonalproductdiscounter.model.enums.Color;
import com.codecool.seasonalproductdiscounter.model.enums.Season;
import com.codecool.seasonalproductdiscounter.model.products.Product;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.persistence.SqliteConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository{
    private final Logger logger;
    private final SqliteConnector sqliteConnector;

    public ProductRepositoryImpl(Logger logger, SqliteConnector sqliteConnector) {
        this.logger = logger;
        this.sqliteConnector = sqliteConnector;
    }

    @Override
    public List<Product> getAvailableProducts() {
        List<Product> avaibleProducts = new ArrayList<>();

        String sqlQuery = "SELECT * FROM products WHERE sold = 0";
        Connection connection = sqliteConnector.getConnection();

        try{
            collectAvaibleProducts(connection, sqlQuery, avaibleProducts);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }

        avaibleProductsLogInfo(avaibleProducts);
        return avaibleProducts;
    }

    private void avaibleProductsLogInfo(List<Product> avaibleProducts) {
        if(avaibleProducts.size() > 0){
            logger.logInfo("All available products have been collected (" + avaibleProducts.size() +")");
        } else{
            logger.logInfo("There is no available product in the database");
        }
    }

    private void collectAvaibleProducts(Connection connection, String sqlQuery, List<Product> avaibleProducts) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Color color = Color.valueOf(resultSet.getString("color"));
            Season season = Season.valueOf(resultSet.getString("season"));
            double price = resultSet.getDouble("price");
            boolean sold = resultSet.getInt("sold") == 1;

            Product product = new Product(id, name, color, season, price, sold);
            avaibleProducts.add(product);
        }
    }

    @Override
    public boolean addProducts(List<Product> products) {
        String sqlQuery = "INSERT INTO products VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = sqliteConnector.getConnection();
        int successfullInsertionCount = 0;
        try{
            successfullInsertionCount = insertRecordIntoDatabase(products, connection, sqlQuery);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }

        return insertionSuccess(products, successfullInsertionCount);
    }

    private boolean insertionSuccess(List<Product> products, int successfullInsertionCount) {
        if(successfullInsertionCount == products.size()){
            logger.logInfo("Every product was successfully inserted into the database");
            return true;
        } else{
            logger.logError("Something went wrong!" + (products.size() - successfullInsertionCount) + " record have been lost!\nPlease try to insert the records again!");
            return false;
        }
    }

    public int insertRecordIntoDatabase(List<Product> products, Connection connection, String sqlQuery) throws SQLException {
        int successfullInsertionCount = 0;
        for(Product product : products){
            PreparedStatement preparedStatement = getPreparedStatementForInsertion(connection, sqlQuery, product);

            if(preparedStatement.execute()){
                successfullInsertionCount++;
            }
        }

        return successfullInsertionCount;
    }

    private static PreparedStatement getPreparedStatementForInsertion(Connection connection, String sqlQuery, Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, product.id());
        preparedStatement.setString(2, product.name());
        preparedStatement.setString(3, product.color().name());
        preparedStatement.setString(4, product.season().name());
        preparedStatement.setDouble(5, product.price());
        preparedStatement.setInt(6, product.sold() ? 1 : 0);
        return preparedStatement;
    }

    @Override
    public boolean setProductAsSold(Product product) {
        String sqlQuery = "UPDATE products SET sold = 1 WHERE id = ?;";
        Connection connection = sqliteConnector.getConnection();
        boolean updateSuccess = false;

        try{
            updateSuccess = updatingTheSoldField(product, connection, sqlQuery, updateSuccess);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }


        return updateSuccess;
    }

    private boolean updatingTheSoldField(Product product, Connection connection, String sqlQuery, boolean updateSuccess) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, product.id());
        updateSuccess = preparedStatement.execute();
        logger.logInfo("Product " + product.id() + "|" + product.name() + " Has been sold!");
        return updateSuccess;
    }
}
