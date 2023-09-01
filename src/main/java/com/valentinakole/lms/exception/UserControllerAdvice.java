package com.valentinakole.lms.exception;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.exception.errors.EmailExistError;
import com.valentinakole.lms.exception.errors.UserNotFoundError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class UserControllerAdvice {
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelBadRequestException(BadRequestError e) {
        log.error(e.getMessage());
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserNotFoundException(UserNotFoundError e) {
        log.error("The user was not found");
        UserErrorResponse response = new UserErrorResponse("The user with this ID was not found", System.currentTimeMillis());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserEmailException(EmailExistError e) {
        log.error("This email already exist");
        UserErrorResponse response = new UserErrorResponse("This email already exist", System.currentTimeMillis());
        return ResponseEntity.status(409).body(response);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handelUserEmailException(Exception e) {
        log.error(e.getMessage());
        UserErrorResponse response = new UserErrorResponse("Internet Server Exception", System.currentTimeMillis());
        return ResponseEntity.status(500).body(response);
    }
}
