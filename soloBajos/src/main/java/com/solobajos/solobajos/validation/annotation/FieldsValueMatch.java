package com.solobajos.solobajos.validation.annotation;

import com.solobajos.solobajos.validation.validator.FieldsValueMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsValueMatchValidator.class)
@Documented
public @interface FieldsValueMatch {

    String message() default "Values does not match";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};

    String field();
    String fieldMatch();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }

}