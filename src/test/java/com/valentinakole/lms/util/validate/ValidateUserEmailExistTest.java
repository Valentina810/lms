package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateUserEmailExistTest {
    @Mock
    private UserServiceImpl userService;
    @Mock
    private BindingResult bindingResult;
    @InjectMocks
    private ValidateUserEmailExist validateUserEmailExist;
    private Long id;
    private User newUser;
    private User oldUser;

    @BeforeEach
    void setUp() {
        id = 1L;
        newUser = new User(id, "Ivan", "Ivanov", "login", "password", null,
                "ivan@mail.com", LocalDate.of(2000, 01, 01), null, "url");
        oldUser = new User(id, "Ivan", "Ivanov", "login", "password", null,
                "ivan@mail.com", LocalDate.of(2000, 01, 01), null, "url");
    }


    @Test
    void validate_whenUserWithEmailExistInTable_thenThrowEmailExistError() {
        oldUser.setId_user(id + 1);

        when(userService.findByEmail(newUser.getEmail())).thenReturn(Optional.of(oldUser));

        Assertions.assertThrows(EmailExistError.class, () -> validateUserEmailExist.validate(newUser, bindingResult));
        verify(userService).findByEmail(newUser.getEmail());
    }

    @Test
    void validate_whenUserWithEmailSameUserInTable_thenReturnVoid() {
        when(userService.findByEmail(newUser.getEmail())).thenReturn(Optional.of(oldUser));

        validateUserEmailExist.validate(newUser, bindingResult);

        verify(userService).findByEmail(newUser.getEmail());
    }

    @Test
    void validate_whenUserWithEmailNotExistInTable_thenReturnVoid() {
        when(userService.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());

        validateUserEmailExist.validate(newUser, bindingResult);

        verify(userService).findByEmail(newUser.getEmail());
    }
}