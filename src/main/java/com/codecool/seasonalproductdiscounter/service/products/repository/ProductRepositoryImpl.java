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

        if(avaibleProducts.size() > 0){
            logger.logInfo("All available products have been collected (" + avaibleProducts.size() +")");
        } else{
            logger.logInfo("There is no available product in the database");
        }
        return avaibleProducts;
    }

    private static void collectAvaibleProducts(Connection connection, String sqlQuery, List<Product> avaibleProducts) throws SQLException {
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
        return false;
    }

    @Override
    public boolean setProductAsSold(Product product) {
        return false;
    }
}
