package com.valentinakole.lms.util.annotation;

import com.valentinakole.lms.util.validate.ValidateDateBirth;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateDateBirth.class)
public @interface ValidDateBirth {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
