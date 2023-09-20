package com.valentinakole.lms.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность 'Пользователь' (создание/редактирование)")
public class UserRequestDto {

    @Pattern(message = "Имя должно содержать только буквы", regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @NotBlank(message = "Имя обязательно для заполнения")
    @Size(max = 250, message = "Имя должно быть не больше 250 знаков")
    @Schema(description = "Имя", example = "Ирина")
    private String name;

    @Pattern(message = "Фамилия должна содержать только буквы", regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @Size(max = 250, message = "Фамилия должна быть не больше 250 знаков")
    @Schema(description = "Фамилия", example = "Савельева")
    private String surname;

    @NotBlank(message = "Login не должен быть пустым")
    @Size(max = 100, message = "Login должен быть не больше 100 знаков")
    @Schema(description = "Логин", example = "irinasav")
    private String login;

    @Size(max = 100, message = "Пароль должен быть не больше 100 знаков")
    @Schema(description = "Пароль", example = "TY89*tQW!k")
    private String password;

    @NotBlank(message = "Поле Email обязательно для заполнения")
    @Size(max = 250, message = "Email должен быть не больше 250 знаков")
    @Email(message = "Email должна иметь правильный формат. Пример ivan@yandex.ru или petr@gmail.com")
    @Schema(description = "Email", example = "irinasav@yandex.ru")
    private String email;

    @Past(message = "День рождения должен быть не позднее сегодняшнего дня")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Дата рождения", example = "2000-09-14")
    private LocalDate dateBirth;

    @URL(message = "Неправильный формат URL, пример https://habr.com/")
    @Size(max = 1000, message = "Длина адреса должен быть не больше 1000 знаков")
    @Schema(description = "Урл, по которому хранится аватар", example = "https://habr.com/ru/irinasav.jpg")
    private String avatarUrl;
}
