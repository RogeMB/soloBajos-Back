package com.solobajos.solobajos.dto;


import javax.validation.constraints.NotBlank;

public record LoginRequest (@NotBlank(message = "{loginUserDto.login.notblank}")
                            String username,
                            @NotBlank(message = "{loginUserDto.login.notblank}")
                            String password) {
}