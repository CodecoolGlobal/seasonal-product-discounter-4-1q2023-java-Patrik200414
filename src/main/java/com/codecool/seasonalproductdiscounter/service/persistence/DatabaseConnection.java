package com.codecool.seasonalproductdiscounter.service.persistence;

import com.codecool.seasonalproductdiscounter.service.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String dbFile;
    private final Logger logger;

    public DatabaseConnection(String dbFile, Logger logger) {
        this.dbFile = dbFile;
        this.logger = logger;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbFile);
        } catch (SQLException e) {
            logger.logError(e.getMessage());
        }

        return conn;
    }
}
