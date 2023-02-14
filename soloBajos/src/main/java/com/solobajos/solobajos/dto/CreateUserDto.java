package com.solobajos.solobajos.dto;

import lombok.Builder;

@Builder
public record CreateUserDto (String username,
                             String password,
                             String verifyPassword,
                             String avatar,
                             String fullName){


}
