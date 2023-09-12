package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Test
    void findById_whenUserExist_thenReturnUser() {
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));

        Assertions.assertEquals(userRepository.findById(user.getId_user()).get(), user);
    }

    @Test
    void findById_whenUserNotExist_thenThrowBadRequestError() {
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(0L));
        verify(userRepository).findById(0L);
    }

    @Test
    void create_() {
    }

    @Test
    void update() {
    }

    @Test
    void findByEmail() {
    }
}