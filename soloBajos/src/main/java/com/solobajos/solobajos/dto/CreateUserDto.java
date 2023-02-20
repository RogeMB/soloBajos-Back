package com.solobajos.solobajos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solobajos.solobajos.validation.annotation.FieldsValueMatch;
import com.solobajos.solobajos.validation.annotation.StrongPassword;
import com.solobajos.solobajos.validation.annotation.UniqueEmail;
import com.solobajos.solobajos.validation.annotation.UniqueUsername;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password", fieldMatch = "verifyPassword",
                message = "{createUserDto.password.nomatch}"
        ),
        @FieldsValueMatch(
                field = "email", fieldMatch = "verifyEmail",
                message = "{createUserDto.emails.nomatch}"
        )
})
public record CreateUserDto (
        @UniqueUsername(message = "{createUserDto.username.unique}")
        @NotEmpty(message = "{createUserDto.username.notempty}")
        @NotBlank(message = "{createUserDto.username.notblank}")
        String username,
        @NotEmpty(message = "{createUserDto.fullname.notempty}")
        @NotBlank(message = "{createUserDto.fullname.notblank}")
        String fullName,

        @UniqueEmail(message = "{createUserDto.email.unique}")
        @NotEmpty(message = "{createUserDto.email.notempty}")
        @NotBlank(message = "{createUserDto.email.notblank}")
        @Email(message = "{createUserDto.email.email}")
        String email,

        @Email(message = "{createUserDto.email.email}")
        @NotEmpty(message = "{createUserDto.email.notempty}")
        @NotBlank(message = "{createUserDto.email.notblank}")
        String verifyEmail,
        @StrongPassword(message = "{createUserDto.password.strong}")
        @NotEmpty(message = "{createUserDto.password.notempty}")
        @NotBlank(message = "{createUserDto.password.notblank}")
        String password,

        @NotEmpty(message = "{createUserDto.verifyPassword.notempty}")
        @NotBlank(message = "{createUserDto.verifyPassword.notblank}")
        String verifyPassword
        ){
}
