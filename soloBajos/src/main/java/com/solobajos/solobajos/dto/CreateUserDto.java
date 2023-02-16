package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.validation.annotation.FieldsValueMatch;
import com.solobajos.solobajos.validation.annotation.StrongPassword;
import com.solobajos.solobajos.validation.annotation.UniqueUsername;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
        @NotBlank(message = "{createUserDto.blankspaces.notblank}")
        String username,
        @StrongPassword
        @NotEmpty(message = "{createUserDto.password.notempty}")
        @NotBlank(message = "{createUserDto.blankspaces.notblank}")
        String password,

        @NotEmpty(message = "{createUserDto.verifypassword.notempty}")
        @NotBlank(message = "{createUserDto.blankspaces.notblank}")
        String verifyPassword,
        @URL(message = "{createUserDto.avatar.url}")
        String avatar,
        @NotEmpty(message = "{createUserDto.fullname.notempty}")
        @NotBlank(message = "{createUserDto.blankspaces.notblank}")
        String fullName){
/*
  @NotEmpty(message = "{createUserDto.email.notempty}")
                             @Email(message = "{createUserDto.email.email}")
                             @NotBlank(message = "{createUserDto.blankspaces.notblank}")
                             @UniqueEmail(message = "{createUserDto.email.unique}")
                             String email,

                             @Email(message = "{createUserDto.email.email}")
                             @NotBlank(message = "{createUserDto.blankspaces.notblank}")
                             @NotEmpty(message = "{createUserDto.email.notempty}")
                             String verifyEmail,
*/

}
