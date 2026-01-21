package com.backend.service;

import com.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void registerUserTest() {

    }

    @Test
    void authenticateTest() {

    }
    @Test
    void getUserHistoryTest() {

    }
    @Test
    void getUserPreferencesTest() {

    }
}