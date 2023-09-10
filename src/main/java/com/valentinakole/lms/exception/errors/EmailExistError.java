package com.valentinakole.lms.exception.errors;

public class EmailExistError extends RuntimeException {
    public EmailExistError(String message) {
        super(message);
    }
}
