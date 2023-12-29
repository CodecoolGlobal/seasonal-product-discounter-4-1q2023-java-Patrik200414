package com.codecool.seasonalproductdiscounter.service.users;

import com.codecool.seasonalproductdiscounter.model.users.User;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.persistence.SqliteConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository{
    private final Logger logger;
    private final SqliteConnector sqliteConnector;

    public UserRepositoryImpl(Logger logger, SqliteConnector sqliteConnector) {
        this.logger = logger;
        this.sqliteConnector = sqliteConnector;
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "SELECT * FROM users;";
        Connection connection = sqliteConnector.getConnection();
        List<User> users = new ArrayList<>();
        try{
            collectUsers(connection, sqlQuery, users);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }

        getUsersLogInfo(users);

        return users;
    }

    private void getUsersLogInfo(List<User> users) {
        if(users.size() > 0){
            logger.logInfo("All users have been collected (" + users.size() +")");
        } else{
            logger.logInfo("There is no users in the database");
        }
    }

    private static void collectUsers(Connection connection, String sqlQuery, List<User> users) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String userName = resultSet.getString("user_name");
            String password = resultSet.getString("password");

            User user = new User(id, userName, password);
            users.add(user);
        }
    }

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean changeUserName(User user, String userName) {
        return false;
    }

    @Override
    public boolean changePassword(User user, String password) {
        return false;
    }
}
