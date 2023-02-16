package com.solobajos.solobajos.dto;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;

@Builder
public record ChangePasswordRequest (@NotEmpty(message = "{userDto.password.notempty}") String oldPassword,
                                     @NotEmpty(message = "{userDto.password.notempty}") String newPassword,
                                     @NotEmpty(message = "{userDto.password.notempty}")String verifyNewPassword) {
}


// probar si funciona con record