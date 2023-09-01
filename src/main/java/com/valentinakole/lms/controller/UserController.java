package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.UserRequestDto;
import com.valentinakole.lms.dto.UserResponseGetDto;
import com.valentinakole.lms.dto.UserResponsePatchDto;
import com.valentinakole.lms.dto.UserTokenDto;
import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.models.User;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.UserValidator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "The user was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseGetDto> findById(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id) {
        return ResponseEntity.status(200).body(new ModelMapper().map(userService.findById(id), UserResponseGetDto.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The user was successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "409", description = "This email already exists"),
            @ApiResponse(responseCode = "404", description = "The user was not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<UserTokenDto> create(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        User user = validateUser(userRequestDto, bindingResult, 0L);

        return ResponseEntity.status(201).body(new ModelMapper().map(userService.create(user), UserTokenDto.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The user was successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "409", description = "This email already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponsePatchDto> update(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id,
                                                       @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {

        User user = validateUser(userRequestDto, bindingResult, id);

        return ResponseEntity.status(200).body(new ModelMapper().map(userService.update(id, user), UserResponsePatchDto.class));
    }

    private User validateUser(UserRequestDto userRequestDto, BindingResult bindingResult, Long id) {
        User user = new ModelMapper().map(userRequestDto, User.class);
        user.setId_user(id);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(" ; ");
            }
            throw new BadRequestError(errorMessage.toString());
        }
        userValidator.validate(user, bindingResult);
        return user;
    }

}
