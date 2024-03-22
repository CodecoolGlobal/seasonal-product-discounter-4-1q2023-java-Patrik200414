package com.codecool.seasonalproductdiscounter.service.authentication;

import com.codecool.seasonalproductdiscounter.model.users.User;
import com.codecool.seasonalproductdiscounter.service.logger.ConsoleLogger;
import com.codecool.seasonalproductdiscounter.service.logger.Logger;
import com.codecool.seasonalproductdiscounter.service.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepository.class);
    }

    @Test
    void authenticate_UserRepositoryMockedReturnsExpectedUser_ShouldReturnTrue() {
        Logger logger = new ConsoleLogger();
        User expectedUser = new User(1, "TestUser", "Password1");
        when(userRepository.getUserByUserName("TestUser")).thenReturn(
                expectedUser
        );

        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, logger);


        boolean result = authenticationService.authenticate(expectedUser);

        assertTrue(result);

    }

    @Test
    void authenticate_UserRepositoryMockedReturnNull_ShouldReturnFalse() {
        Logger logger = new ConsoleLogger();
        User expectedUser = new User(1, "TestUser", "Password1");
        when(userRepository.getUserByUserName("TestUser")).thenReturn(
                null
        );

        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, logger);


        boolean result = authenticationService.authenticate(expectedUser);

        assertFalse(result);

    }
}