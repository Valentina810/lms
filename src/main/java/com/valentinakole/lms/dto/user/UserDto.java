package com.valentinakole.lms.dto.user;

import com.valentinakole.lms.util.validate.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long userId;

    @Pattern(message = "Имя должно содержать только буквы", regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @NotBlank(message = "Имя обязательно для заполнения")
    @Size(max = 250, message = "Имя должно быть не больше 250 знаков")
    private String name;

    @Pattern(message = "Фамилия должна содержать только буквы", regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @Size(max = 250, message = "Фамилия должна быть не больше 250 знаков")
    private String surname;

    @NotBlank(message = "Login не должен быть пустым")
    @Size(max = 100, message = "Login должен быть не больше 100 знаков")
    private String login;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(max = 100, message = "Пароль должен быть не больше 100 знаков")
    private String password;

    @NotBlank(message = "Поле Email обязательно для заполнения")
    @Size(max = 250, message = "Email должен быть не больше 250 знаков")
    @Email(message = "Email должна иметь правильный формат. Пример ivan@yandex.ru или petr@gmail.com")
    private String email;

    @Pattern(message = ValidationMessage.VALIDATION_INCORRECT_JSON_OBJECT, regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String dateBirth;

    @URL(message = "Неправильный формат URL, пример https://habr.com/")
    @Size(max = 1000, message = "Длина адреса должен быть не больше 1000 знаков")
    private String avatarUrl;
}
