package com.codecool.seasonalproductdiscounter.service.transactions.repository;

import com.codecool.seasonalproductdiscounter.model.enums.Color;
import com.codecool.seasonalproductdiscounter.model.enums.Season;
import com.codecool.seasonalproductdiscounter.model.products.Product;
import com.codecool.seasonalproductdiscounter.model.transactions.Transaction;
import com.codecool.seasonalproductdiscounter.model.users.User;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.persistence.SqliteConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepositoryImpl implements TransactionRepository{
    private final Logger logger;
    private final SqliteConnector sqliteConnector;

    public TransactionRepositoryImpl(Logger logger, SqliteConnector sqliteConnector) {
        this.logger = logger;
        this.sqliteConnector = sqliteConnector;
    }

    @Override
    public boolean add(Transaction transaction) {
        String sqlQuery = "INSERT INTO transactions VALUES(?, ?, ?, ?, ?);";
        Connection connection = sqliteConnector.getConnection();
        boolean success = false;
        try{
            logger.logInfo("A new transaction have been made! Transaction id: " + transaction.id());
            addingTransactionSuccess(transaction, connection, sqlQuery);
            success = true;

        } catch (SQLException e){
            logger.logError(e.getMessage());
        }
        return success;
    }

    private static boolean addingTransactionSuccess(Transaction transaction, Connection connection, String sqlQuery) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, transaction.id());
        preparedStatement.setString(2, String.valueOf(transaction.date()));
        preparedStatement.setInt(3, transaction.user().id());
        preparedStatement.setInt(4, transaction.product().id());
        preparedStatement.setDouble(5, transaction.pricePaid());

        return preparedStatement.execute();
    }

    @Override
    public List<Transaction> getAll() {
        String sqlQuery = "SELECT transactions.id AS transaction_id, date, price_paid, products.id AS product_id, products.name AS product_name, color, season, price, sold, users.id AS user_id, user_name, password" +
            " FROM transactions INNER JOIN products ON transactions.product_id = products.id" +
            " INNER JOIN users ON transactions.user_id = users.id;";

        List<Transaction> transactions = new ArrayList<>();
        Connection connection = sqliteConnector.getConnection();
        try{
            logger.logInfo("Collecting transactions from the database!");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            addTransactions(resultSet, transactions);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }
        return transactions;
    }

    private void addTransactions(ResultSet resultSet, List<Transaction> transactions) throws SQLException {
        while (resultSet.next()){
            Product product = collectProductData(resultSet);
            User user = collectUserData(resultSet);

            Transaction transaction = collectTransactionData(resultSet, user, product);
            transactions.add(transaction);
        }
    }

    private Transaction collectTransactionData(ResultSet resultSet, User user, Product product) throws SQLException {
        int transactionId = resultSet.getInt("transaction_id");
        String[] transactionDate = resultSet.getString("date").split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(transactionDate[0]), Integer.parseInt(transactionDate[1]), Integer.parseInt(transactionDate[2]));
        double pricePaid = resultSet.getDouble("price_paid");

        return new Transaction(transactionId, date, user, product, pricePaid);
    }

    private User collectUserData(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("user_id");
        String userName = resultSet.getString("user_name");
        String password = resultSet.getString("password");

        return new  User(userId, userName, password);
    }

    private Product collectProductData(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("product_id");
        String productName = resultSet.getString("product_name");
        Color color = Color.valueOf(resultSet.getString("color"));
        Season season = Season.valueOf(resultSet.getString("season"));
        double price = resultSet.getDouble("price");
        boolean sold = resultSet.getInt("sold") == 1 ? true : false;

        return new Product(productId, productName, color, season, price, sold);
    }

    @Override
    public boolean removeData() {
        String sqlQuery = "DELETE FROM transactions;";
        Connection connection = sqliteConnector.getConnection();
        boolean success = false;

        try{
            Statement statement = connection.createStatement();
            statement.execute(sqlQuery);
            success = true;
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }
        return success;
    }
}
