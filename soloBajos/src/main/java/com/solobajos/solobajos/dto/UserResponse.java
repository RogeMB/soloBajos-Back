package com.solobajos.solobajos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;


@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    protected String id;
    protected String username;
    protected String fullName;

    protected String email;
    protected String avatar;

    protected Boolean enabled;

    protected Set<UserRole> roles;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    protected LocalDateTime createdAt;

    public static UserResponse fromUser(User user) {

        return UserResponse.builder()
                .id(user.getId().toString())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .enabled(user.isEnabled())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .build();
    }

}