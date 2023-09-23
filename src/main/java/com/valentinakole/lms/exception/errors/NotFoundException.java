package com.valentinakole.lms.exception.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String objectName, long objectId) {
        super(objectName + " c id " + objectId + " не найден.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
