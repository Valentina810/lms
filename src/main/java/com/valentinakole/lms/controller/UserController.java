package com.valentinakole.lms.controller;

import com.valentinakole.lms.dto.user.UserRequestDto;
import com.valentinakole.lms.dto.user.UserResponseDto;
import com.valentinakole.lms.dto.user.UserResponseGetDto;
import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.model.User;
import com.valentinakole.lms.service.UserService;
import com.valentinakole.lms.util.validate.ValidateUser;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ValidateUser validateUser;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Хорошо"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseGetDto> findById(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id) {
        return ResponseEntity.status(200).body(new ModelMapper().map(userService.findById(id), UserResponseGetDto.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "В теле запроса ошибка (неверное название поля, не указан обязательный параметр)"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует в БД"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        User user = new ModelMapper().map(userRequestDto, User.class);
        if(Objects.equals(user.getPassword(),"")){
            throw new BadRequestError("Пароль не должен быть пустым");
        }
        validateUser.validateUser(user, bindingResult);
        return ResponseEntity.status(201).body(new ModelMapper().map(userService.create(user), UserResponseDto.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные пользователя успешно изменены"),
            @ApiResponse(responseCode = "400", description = "В теле запроса ошибка (неверное название поля, не указан обязательный параметр)"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован (не представился)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует в БД"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable("id") @Parameter(description = "Идентификатор user-а") long id,
                                                  @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        User user = new ModelMapper().map(userRequestDto, User.class);
        user.setId_user(id);
        validateUser.validateUser(user, bindingResult);
        return ResponseEntity.status(200).body(new ModelMapper().map(userService.update(id, user), UserResponseDto.class));
    }
}
