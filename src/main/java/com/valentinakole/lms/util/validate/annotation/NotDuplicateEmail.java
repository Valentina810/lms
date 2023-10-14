package com.valentinakole.lms.util.validate.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = NotDuplicateEmailValidator.class)
@Documented
public @interface NotDuplicateEmail {

    String message() default "{CapitalLetter.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}