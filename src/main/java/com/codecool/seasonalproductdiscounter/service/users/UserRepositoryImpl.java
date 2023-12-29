package com.codecool.seasonalproductdiscounter.service.users;

import com.codecool.seasonalproductdiscounter.model.users.User;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.persistence.SqliteConnector;

import java.sql.*;
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
            logger.logInfo("Collecting users from the database!");
            collectUsers(connection, sqlQuery, users);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }


        return users;
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
    public User getUserByUserName(String searchedUserName) {
        String sqlQuery = "SELECT * FROM users WHERE user_name = ?";
        Connection connection = sqliteConnector.getConnection();
        User searchedUser = null;
        try{
            searchedUser = collectUserByUserName(searchedUserName, connection, sqlQuery);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }
        return searchedUser;
    }


    public User collectUserByUserName(String searchedUserName, Connection connection, String sqlQuery) throws SQLException {
        logger.logInfo("Searching for " + searchedUserName + " user!");
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, searchedUserName);
        ResultSet resultSet = preparedStatement.executeQuery();

        User searchedUser = null;
        searchedUser = convertResultSetToUser(resultSet, searchedUser);

        return searchedUser;
    }

    private static User convertResultSetToUser(ResultSet resultSet, User searchedUser) throws SQLException {
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String userName = resultSet.getString("user_name");
            String password = resultSet.getString("password");

            User user = new User(id, userName, password);
            searchedUser = user;
        }
        return searchedUser;
    }

    @Override
    public boolean addUser(User user) {
        String sqlQuery = "INSERT INTO users VALUES(?, ?, ?);";
        Connection connection = sqliteConnector.getConnection();
        boolean success = false;
        try{
            logger.logInfo("Adding user to the database!");
            success = addUserQuerySuccess(user, connection, sqlQuery, success);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }
        return success;
    }

    private static boolean addUserQuerySuccess(User user, Connection connection, String sqlQuery, boolean success) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, user.id());
        preparedStatement.setString(2, user.userName());
        preparedStatement.setString(3, user.password());

        success = preparedStatement.execute();
        return success;
    }

    @Override
    public boolean changeUserName(User user, String userName) {
        String sqlQuery = "UPDATE users SET user_name = ? WHERE id = ?;";
        Connection connection = sqliteConnector.getConnection();
        boolean success = false;

        try{
            logger.logInfo("Updating " + user + " --> new user name: " + userName);
            success = changeUserDataSuccess(user, userName, connection, sqlQuery);
        } catch (SQLException e){
            logger.logError(e.getMessage());
        }

        return success;
    }


    @Override
    public boolean changePassword(User user, String password) {
        String sqlQuery = "UPDATE users SET password = ? WHERE id = ?;";
        Connection connection = sqliteConnector.getConnection();
        boolean success = false;

        try{
            logger.logInfo("Updating " + user.userName() + "'s password!");
            success = changeUserDataSuccess(user, password, connection, sqlQuery);
        } catch (SQLException e){
            logger.logInfo(e.getMessage());
        }

        return success;
    }

    private boolean changeUserDataSuccess(User user, String newData, Connection connection, String sqlQuery) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1, newData);
        preparedStatement.setInt(2, user.id());
        return preparedStatement.execute();
    }


}
