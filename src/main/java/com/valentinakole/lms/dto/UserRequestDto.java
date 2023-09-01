package com.valentinakole.lms.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

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

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(max = 100, message = "Пароль должен быть не больше 100 знаков")
    private String password;

    @NotEmpty(message = "Email обязательна для заполнения")
    @Size(max = 250, message = "Email должен быть не больше 250 знаков")
    @Email(message = "Email должна иметь правильный формат. Пример ivan@yandex.ru или petr@gmail.com")
    private String email;

    private Date dateBirth;

    @Size(max = 1000, message = "Длина адреса должен быть не больше 1000 знаков")
    private String avatarUrl;
}
