package com.valentinakole.lms.util.validate.annotation;

import com.valentinakole.lms.service.impl.UserServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotDuplicateEmailValidator implements ConstraintValidator<NotDuplicateEmail, String> {

    private final UserServiceImpl userService;

    public NotDuplicateEmailValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || userService.findByEmail(value).isEmpty();
    }
}