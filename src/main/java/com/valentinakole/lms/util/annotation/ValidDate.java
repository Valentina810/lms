package com.valentinakole.lms.util.annotation;

import com.valentinakole.lms.util.validate.ValidateDate;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateDate.class)
public @interface ValidDate {
    String message() default "Неверный параметр запроса: проверьте параметры";

    boolean past() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}