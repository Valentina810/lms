package com.valentinakole.lms.exception;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.exception.errors.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ApiError> handelBadRequestException(BadRequestError e) {
        log.error(e.getMessage());
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .date(LocalDateTime.now()).build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelUserNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .date(LocalDateTime.now()).build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelUserEmailException(EmailExistError e) {
        log.error("Эта электронная почта уже существует в базе данных");
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message("Эта электронная почта уже существует в базе данных")
                .date(LocalDateTime.now()).build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelUserEmailException(HttpServerErrorException e) {
        log.error(e.getMessage());
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Внутренняя ошибка сервера")
                .date(LocalDateTime.now()).build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
