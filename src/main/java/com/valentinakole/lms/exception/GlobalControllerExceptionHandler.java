package com.valentinakole.lms.exception;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.exception.errors.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ApiError> handelHttpMessageNotReadableException(JsonParseException e) {
        return getResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelBadRequestException(BadRequestError e) {
        return getResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return getResponseError("Неверная структура объекта: проверьте скобки, запятые и названия полей", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelHttpServerErrorException(ConstraintViolationException e) {
        return getResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return getResponseError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelNotFoundException(NotFoundException e) {
        return getResponseError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelUserEmailException(EmailExistError e) {
        return getResponseError(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ApiError> handelHttpServerErrorException(HttpServerErrorException e) {
        return getResponseError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiError> getResponseError(String exceptionMessage, HttpStatus badRequest) {
        log.error(exceptionMessage);
        ApiError apiError = ApiError.builder()
                .status(badRequest)
                .message(exceptionMessage)
                .date(LocalDateTime.now()).build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
