package com.valentinakole.lms.service.impl;

import com.valentinakole.lms.exception.errors.NotFoundException;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    private User user;
    private Long id;

    @BeforeEach
    void setUp() {
        id = 0L;
        user = new User(id, "Ivan", "Ivanov", "login", "password", null,
                "ivan@mail.com", LocalDate.of(2000, 01, 01), null, "url");
    }

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
    void create_whenInvoked_thenSaveUser() {
        User mockUser = user;
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        User writtenUser = userService.create(mockUser);

        Assertions.assertEquals(mockUser, writtenUser);
        Assertions.assertEquals(user.getName(), writtenUser.getName());
        Assertions.assertEquals(user.getSurname(), writtenUser.getSurname());
        Assertions.assertEquals(user.getLogin(), writtenUser.getLogin());
        Assertions.assertEquals(user.getPassword(), writtenUser.getPassword());
        Assertions.assertEquals(user.getEmail(), writtenUser.getEmail());
        Assertions.assertEquals(user.getDateBirth(), writtenUser.getDateBirth());
        Assertions.assertNotNull(writtenUser.getDateRegistration());
        Assertions.assertEquals(user.getAvatarUrl(), writtenUser.getAvatarUrl());
        verify(userRepository).save(user);
    }

    @Test
    void update_whenInvoked_thenUser() {
        User oldUser = new User(id, "Petr", "Petrov", "login", "password", null,
                "ivan@mail.com", LocalDate.of(2000, 01, 01), null, "url");

        when(userRepository.findById(id)).thenReturn(Optional.of(oldUser));

//        User writtenUser = userService.create(mockUser);
//
//        Assertions.assertEquals(mockUser, writtenUser);
//        Assertions.assertEquals(user.getName(), writtenUser.getName());
//        Assertions.assertEquals(user.getSurname(), writtenUser.getSurname());
//        Assertions.assertEquals(user.getLogin(), writtenUser.getLogin());
//        Assertions.assertEquals(user.getPassword(), writtenUser.getPassword());
//        Assertions.assertEquals(user.getEmail(), writtenUser.getEmail());
//        Assertions.assertEquals(user.getDateBirth(), writtenUser.getDateBirth());
//        Assertions.assertNotNull(writtenUser.getDateRegistration());
//        Assertions.assertEquals(user.getAvatarUrl(), writtenUser.getAvatarUrl());
//        verify(userRepository).save(user);
    }

    @Test
    void findByEmail_whenEmailFound_thenReturnUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> optional = userService.findByEmail(user.getEmail());

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(optional.get(), user);
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void findByEmail_whenEmailNotFound_thenReturnEmpty() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Optional<User> optional = userService.findByEmail(user.getEmail());

        Assertions.assertTrue(optional.isEmpty());
        verify(userRepository).findByEmail(user.getEmail());
    }
}