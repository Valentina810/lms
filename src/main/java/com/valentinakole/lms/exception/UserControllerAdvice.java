package com.valentinakole.lms.exception;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.exception.errors.UserNotFoundError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class UserControllerAdvice {
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelBadRequestException(BadRequestError e) {
        log.error(e.getMessage());
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserNotFoundException(UserNotFoundError e) {
        log.error("Пользователь не найден");
        UserErrorResponse response = new UserErrorResponse("Пользователь с таким ID не найден", LocalDateTime.now());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserEmailException(EmailExistError e) {
        log.error("Эта электронная почта уже существует в базе данных");
        UserErrorResponse response = new UserErrorResponse("Эта электронная почта уже существует в базе данных", LocalDateTime.now());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserEmailException(HttpServerErrorException e) {
        log.error(e.getMessage());
        UserErrorResponse response = new UserErrorResponse("Внутренняя ошибка сервера", LocalDateTime.now());
        return ResponseEntity.status(500).body(response);
    }
}
