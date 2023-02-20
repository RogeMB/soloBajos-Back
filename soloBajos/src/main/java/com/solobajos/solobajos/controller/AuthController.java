package com.solobajos.solobajos.controller;


import com.solobajos.solobajos.dto.CreateUserDto;
import com.solobajos.solobajos.dto.JwtUserResponse;
import com.solobajos.solobajos.dto.LoginRequest;
import com.solobajos.solobajos.dto.UserResponse;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.security.jwt.JwtProvider;
import com.solobajos.solobajos.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Solo-Bajos API"))
@Tag(name = "Auth", description = "Esta clase implementa Restcontrollers para la autenticacion")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = userService.createUserWithUserRole(createUserDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(createdURI).body(UserResponse.fromUser(user));
    }

    @PostMapping("/admin/auth/register")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = userService.createUserWithAdminRole(createUserDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(createdURI).body(UserResponse.fromUser(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.username(),
                                loginRequest.password()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        if (!user.isEnabled()){
            throw new DisabledException("This user is banned");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JwtUserResponse.of(user, token));
    }


}
