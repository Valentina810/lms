package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.dto.user.UserResponseGetDto;
import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.validate.ValidatePathVariable;
import com.valentinakole.lms.util.validate.ValidateUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ValidateUser validateUser;

    @Mock
    private ValidatePathVariable validatePathVariable;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserRequestDto userRequestDto;
    private Long id;

    @BeforeEach
    void setUp() {
        id = 0L;
        user = new User(id, "Ivan", "Ivanov", "login", "password", null,
                "ivan@mail.com", LocalDate.of(2000, 01, 01), null, "url");

        userRequestDto = new ModelMapper().map(user, UserRequestDto.class);
    }

    @Test
    void findById_whenInvoked_thenReturnStatusOkWithUserInBody() {
        UserResponseGetDto userResponseGetDto = new ModelMapper().map(user, UserResponseGetDto.class);
        Mockito.when(userService.findById(id)).thenReturn(user);

        ResponseEntity<UserResponseGetDto> response = userController.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseGetDto, response.getBody());
        verify(userService).findById(id);
    }

    @Test
    void create_whenUserValid_thenReturnStatusOkWithUserResponseDTOBody() {
        UserResponseDto userResponseDto = new ModelMapper().map(user, UserResponseDto.class);
        Mockito.when(userService.create(user)).thenReturn(user);

        ResponseEntity<UserResponseDto> response = userController.create(userRequestDto, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(response.getBody(), userResponseDto);
        verify(userService).create(user);
        verify(validateUser).validateUser(user, bindingResult);
    }

    @Test
    void create_whenUserNotValidPassword_thenReturnBadRequestException() {
        userRequestDto.setPassword("");

        Assertions.assertThrows(BadRequestError.class, () -> userController.create(userRequestDto, bindingResult));
        verify(userService, never()).create(user);
        verify(validateUser, never()).validateUser(user, bindingResult);
    }

    @Test
    void create_whenUserNotValid_thenThrowEmailExistError() {
        Mockito.when(validateUser.validateUser(user, bindingResult)).thenThrow(EmailExistError.class);

        Assertions.assertThrows(EmailExistError.class, () -> userController.create(userRequestDto, bindingResult));
        verify(userService, never()).create(user);
        verify(validateUser).validateUser(user, bindingResult);
    }

    @Test
    void update_whenInvoked_thenReturnStatusOkWithUserResponseDTOBody() {
        UserResponseDto userResponseDto = new ModelMapper().map(user, UserResponseDto.class);
        Mockito.when(userService.update(id, user)).thenReturn(user);

        ResponseEntity<UserResponseDto> response = userController.update(id, userRequestDto, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), userResponseDto);
        verify(validateUser).validateUser(user, bindingResult);
        verify(userService).update(id, user);
    }

    @Test
    void update_whenUserNotValid_thenThrowEmailExistError() {
        Mockito.when(validateUser.validateUser(user, bindingResult)).thenThrow(EmailExistError.class);

        Assertions.assertThrows(EmailExistError.class, () -> userController.update(id, userRequestDto, bindingResult));
        verify(userService, never()).update(id, user);
        verify(validateUser).validateUser(user, bindingResult);
    }
}