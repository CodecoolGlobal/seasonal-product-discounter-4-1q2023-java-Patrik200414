package com.codecool.seasonalproductdiscounter.service.persistence;

import com.codecool.seasonalproductdiscounter.service.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnector {

    private final String dbFile;
    private final Logger logger;

    public SqliteConnector(String dbFile, Logger logger) {
        this.dbFile = dbFile;
        this.logger = logger;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            // Complete the method
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            logger.logInfo("The connection was created!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
