package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.dto.user.UserResponseGetDto;
import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.validate.ValidateAvatarUrl;
import com.valentinakole.lms.util.validate.ValidatePathVariable;
import com.valentinakole.lms.util.validate.ValidateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ValidateUser validateUser;
    private final ValidatePathVariable validatePathVariable;

    private final ValidateAvatarUrl validateAvatarUrl;

    @GetMapping("/{id}")
    @Operation(summary = "Получение пользователя по id")
    public ResponseEntity<UserResponseGetDto> findById(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id) {
        validatePathVariable.check(id);
        return ResponseEntity.status(200).body(new ModelMapper().map(userService.findById(id), UserResponseGetDto.class));
    }

    @PostMapping
    @Operation(summary = "Создание пользователя")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) throws MalformedURLException, URISyntaxException {
        User user = new ModelMapper().map(userRequestDto, User.class);
        if (Objects.equals(user.getPassword(), "")) {
            throw new BadRequestError("Пароль не должен быть пустым");
        }
        validateAvatarUrl.isValidURL(user.getAvatarUrl());
        validateUser.validateUser(user, bindingResult);
        return ResponseEntity.status(201).body(new ModelMapper().map(userService.create(user), UserResponseDto.class));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменение пользователя")
    public ResponseEntity<UserResponseDto> update(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id,
                                                  @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        validatePathVariable.check(id);
        User user = new ModelMapper().map(userRequestDto, User.class);
        user.setId_user(id);
        validateAvatarUrl.isValidURL(user.getAvatarUrl());
        validateUser.validateUser(user, bindingResult);
        return ResponseEntity.status(200).body(new ModelMapper().map(userService.update(id, user), UserResponseDto.class));
    }
}
