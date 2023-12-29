package com.codecool.seasonalproductdiscounter.service.users;

import com.codecool.seasonalproductdiscounter.model.users.User;

import java.util.List;

public interface UserRepository {
    /*
    List<Product> getAvailableProducts();
    boolean addProducts(List<Product> products);
    boolean setProductAsSold(Product product);
    * */

    List<User> getUsers();
    User getUserByUserName(String userName);
    boolean addUser(User user);
    boolean changeUserName(User user, String userName);
    boolean changePassword(User user, String password);
}
