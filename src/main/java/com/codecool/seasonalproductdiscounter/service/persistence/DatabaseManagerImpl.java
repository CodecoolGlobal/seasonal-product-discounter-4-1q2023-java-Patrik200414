package com.codecool.seasonalproductdiscounter.service.persistence;

import com.codecool.seasonalproductdiscounter.service.logger.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DatabaseManagerImpl implements DatabaseManager {
    public static final String PRODUCTS_TABLE_NAME = "products";
    public static final String USERS_TABLE_NAME = "users";
    public static final String TRANSACTIONS_TABLE_NAME = "transactions";

    private static final String PRODUCTS_TABLE_STATEMENT = String.format(
            "CREATE TABLE IF NOT EXISTS %s ("  +
                    "id INTEGER PRIMARY KEY NOT NULL UNIQUE AUTOINCREMENT, " +
                    "name TEXT NOT NULL," +
                    "color TEXT NOT NULL" +
                    "season TEXT NOT NULL" +
                    "price REAL NOT NULL" +
                    "sold INTEGER NOT NULL);"
            ,PRODUCTS_TABLE_NAME
    );

    private static final String USERS_TABLE_STATEMENT = String.format(
            "CREATE TABLE IF NOT EXISTS %s (" +
                    "id INTEGER UNIQUE NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "user_name TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL);"
            , USERS_TABLE_NAME
    );

    private static final String TRANSACTIONS_TABLE_STATEMENT =
            String.format("CREATE TABLE IF NOT EXISTS %s (" +
                            "id INTEGER PRIMARY KEY, " +
                            "date TEXT NOT NULL, " +
                            "user_id INTEGER NOT NULL, " +
                            "product_id INTEGER NOT NULL, " +
                            "price_paid REAL NOT NULL);",
                    TRANSACTIONS_TABLE_NAME);

    private final List<String> tableStatements;
    private final SqliteConnector sqliteConnector;
    private final Logger logger;

    public DatabaseManagerImpl(SqliteConnector sqliteConnector, Logger logger) {
        this.sqliteConnector = sqliteConnector;
        this.logger = logger;
        tableStatements = List.of(PRODUCTS_TABLE_STATEMENT, USERS_TABLE_STATEMENT, TRANSACTIONS_TABLE_STATEMENT);
    }

    @Override
    public boolean createTables() {
        return executeQueries(tableStatements);
    }

    private boolean executeQueries(Iterable<String> queries) {
        try {
            //Complete the method
            Connection connection = sqliteConnector.getConnection();
            for(String query : queries){
                Statement statement = connection.createStatement();
                statement.executeQuery(query);
            }
        } catch (SQLException e) {
            logger.logError(e.getMessage());
            return false;
        }

        return true;
    }
}
