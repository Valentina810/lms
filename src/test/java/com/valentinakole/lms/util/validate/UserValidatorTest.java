package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserValidator userValidator;


    @Test
    void validate_whenUserWithEmailExistInTable_thenThrowEmailExistError() {
        Long id = 1L;
        User newUser = new User();
        newUser.setEmail("example@mail.com");
        User oldUser = new User();
        oldUser.setId_user(id);
        oldUser.setEmail("example@mail.com");


        when(userService.findByEmail(newUser.getEmail())).thenReturn(Optional.of(oldUser));

        Assertions.assertThrows(EmailExistError.class, () -> userValidator.validate(newUser, bindingResult));
        verify(userService).findByEmail(newUser.getEmail());
    }

    @Test
    void validate_whenUserWithEmailSameUserInTable_thenReturnVoid() {
        Long id = 1L;
        User newUser = new User();
        newUser.setId_user(id);
        newUser.setEmail("example@mail.com");
        User oldUser = new User();
        oldUser.setId_user(id);
        oldUser.setEmail("example@mail.com");
        when(userService.findByEmail(newUser.getEmail())).thenReturn(Optional.of(oldUser));

        userValidator.validate(newUser,bindingResult);

        verify(userService).findByEmail(newUser.getEmail());
    }
    @Test
    void validate_whenUserWithEmailNotExistInTable_thenReturnVoid() {
        Long id = 1L;
        User newUser = new User();
        newUser.setEmail("example@mail.com");
        User oldUser = new User();
        oldUser.setId_user(id);
        oldUser.setEmail("example@mail.com");
        when(userService.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());

        userValidator.validate(newUser,bindingResult);

        verify(userService).findByEmail(newUser.getEmail());
    }
}