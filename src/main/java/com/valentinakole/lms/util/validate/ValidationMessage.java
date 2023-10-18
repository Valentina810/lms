package com.valentinakole.lms.util.validate;

public class ValidationMessage {
    public static final String PROGRESS_MESSAGE = "Прогресс должен быть числом от 0 до 100";
    public static final String INCORRECT_JSON_OBJECT = "Неверная структура объекта: проверьте скобки, запятые и названия полей";
    public static final String INCORRECT_SUBJECT = "Id предмета обязателен для заполнения";
    public static final String INCORRECT_SYMBOL = " должно содержать только буквы";
    public static final String INCORRECT_LENGTH_NAME = "Поле 'Имя' должно содержать не больше 250 символов";
    public static final String INCORRECT_LENGTH_SURNAME = "Поле 'Фамилия' должно содержать не больше 250 символов";
    public static final String INCORRECT_LOGIN = "Поле 'Логин' должно содержать не больше 100 символов";
    public static final String INCORRECT_LENGTH_PASSWORD = "Поле 'Пароль' должно содержать не больше 100 символов";
    public static final String DUPLICATE_EMAIL = "Пользователь с указанной электронной почтой уже существует в базе данных";
    public static final String INCORRECT_FORMAT_EMAIL = "Email должна иметь правильный формат. Пример ivan@yandex.ru или petr@gmail.com";
    public static final String INCORRECT_LENGTH_EMAIL = "Email должен быть не больше 250 знаков";
    public static final String INCORRECT_URL = "Неправильный формат URL, пример https://habr.com/";
    public static final String INCORRECT_LENGTH_URL = "Длина адреса должна быть не больше 1000 знаков";
    public static final String NOT_EMPTY_VALUE = " не должно быть пустым";
    public static final String NOT_SPACES_VALUE = " не должно содержать пробелы";
}