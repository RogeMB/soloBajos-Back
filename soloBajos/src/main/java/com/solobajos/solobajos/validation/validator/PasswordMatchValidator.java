package com.solobajos.solobajos.validation.validator;

import com.solobajos.solobajos.validation.annotation.PasswordMatch;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator  implements ConstraintValidator<PasswordMatch, Object> {

    private String passwordField;
    private String verifyPasswordField;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        passwordField = constraintAnnotation.passwordField();
        verifyPasswordField  = constraintAnnotation.verifyPasswordField();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String password = (String) PropertyAccessorFactory
                .forBeanPropertyAccess(o)
                .getPropertyValue(passwordField);

        String verifyPassword = (String) PropertyAccessorFactory
                .forBeanPropertyAccess(o)
                .getPropertyValue(verifyPasswordField);

        return StringUtils.hasText(password) && password.contentEquals(verifyPassword);
    }
}