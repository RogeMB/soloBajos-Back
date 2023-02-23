package com.solobajos.solobajos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.solobajos.solobajos.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtUserResponse extends UserResponse {

    private String token;

    public JwtUserResponse(UserResponse userResponse) {
        id = userResponse.getId();
        username = userResponse.getUsername();
        fullName = userResponse.getFullName();
        email = userResponse.getEmail();
        avatar = userResponse.getAvatar();
        enabled = userResponse.getEnabled();
        createdAt = userResponse.getCreatedAt();
        roles = userResponse.getRoles();
    }

    public static JwtUserResponse of (User user, String token) {
        JwtUserResponse result = new JwtUserResponse(UserResponse.fromUser(user));
        result.setToken(token);
        return result;

    }
}
