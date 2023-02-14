package com.solobajos.solobajos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solobajos.solobajos.model.User;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record UserResponse (String id,
                            String username,
                            String avatar,
                            String fullName,
                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
                            LocalDateTime createdAt) {

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