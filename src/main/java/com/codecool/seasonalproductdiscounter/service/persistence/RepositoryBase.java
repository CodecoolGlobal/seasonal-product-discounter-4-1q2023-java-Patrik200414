package com.codecool.seasonalproductdiscounter.service.persistence;

import com.codecool.seasonalproductdiscounter.service.logger.Logger;

public abstract class RepositoryBase {

    protected final String tableName;
    protected final DatabaseConnection sqliteConnector;
    protected final Logger logger;

    public RepositoryBase(String tableName, DatabaseConnection sqliteConnector, Logger logger) {
        this.tableName = tableName;
        this.sqliteConnector = sqliteConnector;
        this.logger = logger;
    }
}
