package com.valentinakole.lms.dto.user;

import com.valentinakole.lms.util.validate.annotation.NotDuplicateEmail;
import io.swagger.v3.oas.annotations.media.Schema;
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

import static com.valentinakole.lms.util.validate.ValidationMessage.DUPLICATE_EMAIL;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_FORMAT_EMAIL;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_JSON_OBJECT;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_LENGTH_EMAIL;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_LENGTH_NAME;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_LENGTH_PASSWORD;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_LENGTH_SURNAME;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_LENGTH_URL;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_LOGIN;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_SYMBOL;
import static com.valentinakole.lms.util.validate.ValidationMessage.INCORRECT_URL;
import static com.valentinakole.lms.util.validate.ValidationMessage.NOT_EMPTY_VALUE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность 'Пользователь' (создание)")
public class UserCreateDto {

    @Pattern(message = "Поле 'Имя'" + INCORRECT_SYMBOL, regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @NotBlank(message = "Поле 'Имя'" + NOT_EMPTY_VALUE)
    @Size(max = 250, message = INCORRECT_LENGTH_NAME)
    @Schema(description = "Имя", example = "Ирина")
    private String name;

    @Pattern(message = "Поле 'Фамилия'" + INCORRECT_SYMBOL, regexp = "^[a-zA-Zа-яА-Я]{0,250}$")
    @Size(max = 250, message = INCORRECT_LENGTH_SURNAME)
    @Schema(description = "Фамилия", example = "Савельева")
    private String surname;

    @NotBlank(message = "Поле 'Login'" + NOT_EMPTY_VALUE)
    @Size(max = 100, message = INCORRECT_LOGIN)
    @Schema(description = "Логин", example = "irinasav")
    private String login;

    @NotBlank(message = "Поле 'Пароль'" + NOT_EMPTY_VALUE)
    @Size(max = 100, message = INCORRECT_LENGTH_PASSWORD)
    @Schema(description = "Пароль", example = "TY89*tQW!k")
    private String password;

    @NotDuplicateEmail(message = DUPLICATE_EMAIL)
    @NotBlank(message = "Поле 'Email'" + NOT_EMPTY_VALUE)
    @Size(max = 250, message = INCORRECT_LENGTH_EMAIL)
    @Email(message = INCORRECT_FORMAT_EMAIL)
    @Schema(description = "Email", example = "irinasav@yandex.ru")
    private String email;

    @Pattern(message = INCORRECT_JSON_OBJECT, regexp = "\\d{4}-\\d{2}-\\d{2}")
    @Schema(description = "Дата рождения", example = "2000-09-14")
    private String dateBirth;

    @URL(message = INCORRECT_URL)
    @Size(max = 1000, message = INCORRECT_LENGTH_URL)
    @Schema(description = "Урл, по которому хранится аватар", example = "https://habr.com/ru/irinasav.jpg")
    private String avatarUrl;
}
