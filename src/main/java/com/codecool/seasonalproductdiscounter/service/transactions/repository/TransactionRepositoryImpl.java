package com.codecool.seasonalproductdiscounter.service.transactions.repository;

import com.codecool.seasonalproductdiscounter.model.transactions.Transaction;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.persistence.SqliteConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            success = addingTransactionSuccess(transaction, connection, sqlQuery);

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
        return null;
    }
}
