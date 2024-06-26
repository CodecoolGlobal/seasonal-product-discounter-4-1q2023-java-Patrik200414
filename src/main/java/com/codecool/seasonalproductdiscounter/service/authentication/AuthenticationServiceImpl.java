package com.codecool.seasonalproductdiscounter.service.authentication;

import com.codecool.seasonalproductdiscounter.model.users.User;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.users.UserRepository;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final Logger logger;

    public AuthenticationServiceImpl(UserRepository userRepository, Logger logger) {
        this.userRepository = userRepository;
        this.logger = logger;
    }

    public boolean authenticate(User user) {
        logger.logInfo("Authenticating user!");
        User searchedUser = userRepository.getUserByUserName(user.userName());
        return userChecking(user, searchedUser);
    }

    private boolean userChecking(User user, User searchedUser) {
        boolean isValidUser = false;
        if(searchedUser == null){
            logger.logError("No user found with user name: " + user.userName());
        }else if(!user.password().equals(searchedUser.password())){
            logger.logError("Invalid password for " + user.userName() + "!");
        }else {
            logger.logInfo(user.userName() + " is a valid user!");
            isValidUser = true;
        }
        return isValidUser;
    }
}

