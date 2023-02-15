package com.solobajos.solobajos.validation.annotation;

import javax.validation.Payload;

public @interface UniqueUsername {

    String message() default "Username already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}