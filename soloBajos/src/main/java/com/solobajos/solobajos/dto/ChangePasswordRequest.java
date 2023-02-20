package com.solobajos.solobajos.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
public record ChangePasswordRequest (@NotEmpty(message = "{createUserDto.oldPassword.notempty}")
                                     @NotBlank(message = "{createUserDto.oldPassword.notempty}")
                                     String oldPassword,
                                     @NotEmpty(message = "{createUserDto.newPassword.notempty}")
                                     @NotBlank(message = "{createUserDto.newPassword.notempty}")
                                     String newPassword,
                                     @NotEmpty(message = "{createUserDto.verifyPassword.notempty}")
                                     @NotBlank(message = "{createUserDto.verifyPassword.notempty}")
                                     String verifyNewPassword) {
}
