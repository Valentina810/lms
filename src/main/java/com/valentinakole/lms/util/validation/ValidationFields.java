package com.valentinakole.lms.util.validation;

import com.valentinakole.lms.exception.errors.BadRequestError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationFields {

    private final Validator validator;

    public <T> void validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream().map(e -> e.getMessage() + " ;").collect(Collectors.joining());
            throw new BadRequestError(errorMessage);
        }
    }
}