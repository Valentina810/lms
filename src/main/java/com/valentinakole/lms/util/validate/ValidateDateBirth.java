package com.valentinakole.lms.util.validate;

import com.valentinakole.lms.exception.errors.BadRequestError;
import com.valentinakole.lms.util.annotation.ValidDateBirth;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidateDateBirth implements ConstraintValidator<ValidDateBirth, String> {
    @Override
    public boolean isValid(String birth, ConstraintValidatorContext constraintValidatorContext) {

        LocalDate dateBirth = null;
        try {
            dateBirth = LocalDate.parse(birth);
        } catch (Exception e) {
            throw new BadRequestError("DateBirth не правильный формат даты, число более 12 месяцев или 31 дня , пример 'yyyy-MM-dd' ");
        }
        LocalDate today = LocalDate.now();
        LocalDate past = LocalDate.of(1950, 01, 01);
        if (dateBirth.isAfter(today) || dateBirth.isBefore(past)) {
            throw new BadRequestError("День рождения должно быть не раньше 1950 года и не позже " + LocalDate.now().getYear() + " года ");
        }
        return true;
    }
}
