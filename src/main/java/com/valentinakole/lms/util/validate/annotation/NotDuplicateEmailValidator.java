package com.valentinakole.lms.util.validate.annotation;

import com.valentinakole.lms.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotDuplicateEmailValidator implements ConstraintValidator<NotDuplicateEmail, String> {

    private final UserRepository userRepository;

    public NotDuplicateEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || userRepository.findByEmail(value).isEmpty();
    }
}