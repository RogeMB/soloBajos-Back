package com.solobajos.solobajos.controller;


import com.solobajos.solobajos.dto.CreateUserDto;
import com.solobajos.solobajos.dto.JwtUserResponse;
import com.solobajos.solobajos.dto.LoginRequest;
import com.solobajos.solobajos.dto.UserResponse;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.security.jwt.JwtProvider;
import com.solobajos.solobajos.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Operation(summary = "Este método crea un nuevo user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "26fcbdac-c940-4ca5-9105-5d816d52af03",
                                        "username": "mariab",
                                        "fullName": "María Barrera",
                                        "email": "mariab@hotmail.com",
                                        "avatar": "userDefault.png",
                                        "enabled": true,
                                        "roles": [
                                            "USER"
                                        ],
                                        "createdAt": "23/02/2023 11:51:06"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido correctamente los datos del nuevo user",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> createUserWithUserRole(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = userService.createUserWithUserRole(createUserDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(createdURI).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Este método crea un nuevo admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo admin",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "a520d2e5-16c1-44ce-a120-ed19c862d2bd",
                                        "username": "rogemb",
                                        "fullName": "Roge Mohigefer",
                                        "email": "rogelio@gmail.com",
                                        "avatar": "userDefault.png",
                                        "enabled": true,
                                        "roles": [
                                            "ADMIN"
                                        ],
                                        "createdAt": "17/02/2023 00:00:00",
                                        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhNTIwZDJlNS0xNmMxLTQ0Y2UtYTEyMC1lZDE5Yzg2MmQyYmQiLCJpYXQiOjE2NzcxNDk0MDIsImV4cCI6MTY3ODQ0NTQwMn0.-NMeBaPQ3ohKuyRh-jlDciJYXPuN_6sWDomHGH5OYJNwM8iM51675Iz5m69YtdstbFwsrueZ6vQvNujb44NauA"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido correctamente los datos del nuevo admin",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content),
    })
    @PostMapping("/admin/auth/register")
    public ResponseEntity<UserResponse> createUserWithAdminRole(@Valid @RequestBody CreateUserDto createUserDto) {
        User user = userService.createUserWithAdminRole(createUserDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(createdURI).body(UserResponse.fromUser(user));
    }

    @Operation(summary = "Este método loguea a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha logueado correctamente",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "8c5eedfc-df65-4552-8b37-84507e432a5a",
                                        "username": "javiermb",
                                        "fullName": "Javier Mohigefer",
                                        "email": "javier@gmail.com",
                                        "avatar": "userDefault.png",
                                        "enabled": true,
                                        "roles": [
                                            "USER"
                                        ],
                                        "createdAt": "17/02/2023 00:00:00",
                                        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4YzVlZWRmYy1kZjY1LTQ1NTItOGIzNy04NDUwN2U0MzJhNWEiLCJpYXQiOjE2NzcxNDk0NTksImV4cCI6MTY3ODQ0NTQ1OX0.B804dxz_LHS-hqYKKV_9YNclOkzk2g1_0sxErAQwsYyWllrvkyppAyMhrjWgHf1WobdymLsyo4ewQbew-S-diQ"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido correctamente los datos del usuario",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

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
