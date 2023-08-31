package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.UserRequestDto;
import com.valentinakole.lms.dto.UserResponseGetDto;
import com.valentinakole.lms.dto.UserResponsePatchDto;
import com.valentinakole.lms.dto.UserTokenDto;
import com.valentinakole.lms.models.User;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.*;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/{id}")
    public UserResponseGetDto findById(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id) {
        return new ModelMapper().map(userService.findById(id), UserResponseGetDto.class);
    }

    @PostMapping
    public UserTokenDto create(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        User user = new ModelMapper().map(userRequestDto, User.class);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(" ; ");
            }
            throw new UserNotCreatedError(errorMessage.toString());
        }
        return new ModelMapper().map(userService.create(user), UserTokenDto.class);
    }

    @PatchMapping("/{id}")
    public UserResponsePatchDto update(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id,
                                       @RequestBody UserRequestDto userPostDto) {
        User user = new ModelMapper().map(userPostDto, User.class);
        return new ModelMapper().map(userService.update(id, user), UserResponsePatchDto.class);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserNotCreatedException(UserNotCreatedError e) {
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserNotFoundException(UserNotFoundError e) {
        UserErrorResponse response = new UserErrorResponse("The user with this ID was not found", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserEmailException(EmailExistError e) {
        UserErrorResponse response = new UserErrorResponse("This email already exist", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
