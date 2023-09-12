package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidateUserImplTest {

    @InjectMocks
    private ValidateUserImpl validateUser;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserValidator userValidator;

    @Mock
    private User user;

    @Test
    void validateUser_whenUserNotHasErrors_thenReturnUser() {
        doNothing().when(userValidator).validate(user, bindingResult);

        Assertions.assertEquals(validateUser.validateUser(user, bindingResult), user);
        verify(userValidator).validate(user, bindingResult);
    }

    @Test
    void validateUser_whenUserHasErrorsInUserValidator_thenThrowBadRequestError() {
        doThrow(BadRequestError.class).when(userValidator).validate(user, bindingResult);

        Assertions.assertThrows(BadRequestError.class, () -> userValidator.validate(user, bindingResult));
        verify(userValidator).validate(user, bindingResult);
    }
}