package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.user.FullUserDto;
import com.valentinakole.lms.dto.user.UserCreateDto;
import com.valentinakole.lms.dto.user.UserUpdateDto;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.validate.ErrorsValidationChecker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получение пользователя по id")
    public FullUserDto findById(@PathVariable("id") @Parameter(description = "Идентификатор пользователя") long id) {
        return userService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Создание пользователя")
    @ResponseStatus(HttpStatus.CREATED)
    public FullUserDto create(@RequestBody @Valid UserCreateDto userCreateDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return userService.create(userCreateDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменение пользователя")
    public FullUserDto update(@PathVariable("id") @Parameter(description = "Идентификатор пользователя") long id,
                              @RequestBody @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult) {
        ErrorsValidationChecker.checkValidationErrors(bindingResult);
        return userService.update(id, userUpdateDto);
    }
}
