package com.valentinakole.lms.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность 'Пользователь' (создание/редактирование)")
public class UserRequestDto {

    @NotEmpty(message = "Имя обязательно для заполнения")
    @Size(max = 250, message = "Имя должно быть не больше 250 знаков")
    @Schema(description = "Имя", example = "Ирина")
    private String name;

    @Size(max = 250, message = "Фамилия должна быть не больше 250 знаков")
    @Schema(description = "Фамилия", example = "Савельева")
    private String surname;

    @NotEmpty(message = "Login не должен быть пустым")
    @Size(max = 100, message = "Login должен быть не больше 100 знаков")
    @Schema(description = "Логин", example = "irinasav")
    private String login;

    @Size(max = 100, message = "Пароль должен быть не больше 100 знаков")
    @Schema(description = "Пароль", example = "TY89*tQW!k")
    private String password;

    @NotEmpty(message = "Поле Email обязательно для заполнения")
    @Size(max = 250, message = "Email должен быть не больше 250 знаков")
    @Email(message = "Email должна иметь правильный формат. Пример ivan@yandex.ru или petr@gmail.com")
    @Schema(description = "Email", example = "irinasav@yandex.ru")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "Дата рождения", example = "2000-09-14")
    @Past
    private LocalDate dateBirth;

    @Size(max = 1000, message = "Длина адреса должен быть не больше 1000 знаков")
    @Schema(description = "Урл, по котрому хранится аватар", example = "https://habr.com/ru/irinasav.jpg")
    private String avatarUrl;
}
