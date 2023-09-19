package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.dto.user.UserResponseGetDto;
import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.validate.ValidateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ValidateUser validateUser;

    @GetMapping("/{id}")
    @Operation(summary = "Получение пользователя по id")
    public ResponseEntity<UserResponseGetDto> findById(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id) {
        return ResponseEntity.status(200).body(new ModelMapper().map(userService.findById(id), UserResponseGetDto.class));
    }

    @PostMapping
    @Operation(summary = "Создание пользователя")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        User user = new ModelMapper().map(userRequestDto, User.class);
        if (Objects.equals(user.getPassword(), "")) {
            throw new BadRequestError("Пароль не должен быть пустым");
        }
        validateUser.validateUser(user, bindingResult);
        return ResponseEntity.status(201).body(new ModelMapper().map(userService.create(user), UserResponseDto.class));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменение пользователя")
    public ResponseEntity<UserResponseDto> update(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id,
                                                  @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        User user = new ModelMapper().map(userRequestDto, User.class);
        user.setId_user(id);
        validateUser.validateUser(user, bindingResult);
        return ResponseEntity.status(200).body(new ModelMapper().map(userService.update(id, user), UserResponseDto.class));
    }
}
