package com.solobajos.solobajos.validation.annotation;

import com.solobajos.solobajos.validation.validator.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "Passwords does not match";
    Class<?> [] group() default {};
    Class<? extends Payload> [] payload() default {};

    String passwordField();
    String verifyPasswordField();

}