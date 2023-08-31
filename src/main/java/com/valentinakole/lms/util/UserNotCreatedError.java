package com.valentinakole.lms.util;

public class UserNotCreatedError extends RuntimeException{
    public UserNotCreatedError(String message){
        super(message);
    }
}
