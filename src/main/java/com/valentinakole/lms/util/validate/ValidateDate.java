package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.util.annotation.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidateDate implements ConstraintValidator<ValidDate, String> {
    private boolean isPast;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        isPast = constraintAnnotation.past();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if (date.matches("^\\d+$")) {
            throw new BadRequestError(constraintValidatorContext.getDefaultConstraintMessageTemplate());
        }
        LocalDate dateBirth;
        try {
            dateBirth = LocalDate.parse(date);
        } catch (Exception e) {
            throw new BadRequestError(constraintValidatorContext.getDefaultConstraintMessageTemplate());
        }
        if (isPast && dateBirth.isAfter(LocalDate.now())) {
            throw new BadRequestError("Дата должна быть не позже сегодняшнего дня");
        }
        return true;
    }
}