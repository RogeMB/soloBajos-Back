package com.solobajos.solobajos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solobajos.solobajos.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    protected String id;
    protected String username;
    protected String avatar;
    protected String fullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .build();
    }

}