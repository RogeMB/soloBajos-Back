package com.solobajos.solobajos.validation.annotation;

import com.solobajos.solobajos.validation.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {

    String message() default "Email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}