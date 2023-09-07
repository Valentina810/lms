package com.valentinakole.lms.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDto {

    @NotEmpty(message = "Имя обязательно для заполнения")
    @Size(max = 250, message = "Имя должно быть не больше 250 знаков")
    private String name;

    @Size(max = 250, message = "Фамилия должна быть не больше 250 знаков")
    private String surname;

    @NotEmpty(message = "Login не должен быть пустым")
    @Size(max = 100, message = "Login должен быть не больше 100 знаков")
    private String login;

    @Size(max = 100, message = "Пароль должен быть не больше 100 знаков")
    private String password;

    @NotEmpty(message = "Email обязательна для заполнения")
    @Size(max = 250, message = "Email должен быть не больше 250 знаков")
    @Email(message = "Email должна иметь правильный формат. Пример ivan@yandex.ru или petr@gmail.com")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateBirth;

    @Size(max = 1000, message = "Длина адреса должен быть не больше 1000 знаков")
    private String avatarUrl;
}
