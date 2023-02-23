package com.solobajos.solobajos.controller;

import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.search.util.SearchCriteria;
import com.solobajos.solobajos.search.util.SearchCriteriaExtractor;
import com.solobajos.solobajos.service.StorageService;
import com.solobajos.solobajos.service.UserService;
import com.solobajos.solobajos.utils.MediaTypeUrlResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Solo-Bajos API"))
@Tag(name = "User", description = "Esta clase implementa Restcontrollers para la entidad user")
public class UserController {
    private final UserService userService;
    private final StorageService storageService;
    private static class schemaPageable extends PageDto<UserResponse>{}

    @Operation(summary = "Obtiene todos los users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado users",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = schemaPageable.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "content": [
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
                                                        "createdAt": "17/02/2023 00:00:00"
                                                    },
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
                                                        "createdAt": "17/02/2023 00:00:00"
                                                    }
                                                ],
                                                "last": true,
                                                "first": true,
                                                "totalPages": 1,
                                                "totalElements": 2,
                                                "currentPage": 0
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún user",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe el user que buscaba",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content),
    })
    @GetMapping("/admin/user")
    public PageDto<UserResponse> getAllUsers(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable){

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        return userService.findAllSearch(params, pageable);
    }

    @Operation(summary = "Obtiene la imagen de un user por su id")
    @Parameter(description = "El id del usuario que se quiere buscar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la imagen del user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
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
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún user",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe el user que buscaba",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content),
    })
    @GetMapping("/admin/user/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return UserResponse.fromUser(userService.findById(id));
    }


    @Operation(summary = "Obtiene el perfil de un user autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
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
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún user",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @GetMapping("/user/profile")
    public UserResponse getProfile(@AuthenticationPrincipal User loggedUser) {
        return UserResponse.fromUser(loggedUser);
    }


    @Operation(summary = "Obtiene la imagen de un user autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la imagen del user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Resource.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún user",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "415",
                    description = "Formato no permitido",
                    content = @Content),
    })
    @GetMapping("/user/image")
    public ResponseEntity<Resource> getImage(@AuthenticationPrincipal User user){
        User userFounded= userService.findById(user.getId());
        if(userFounded.getAvatar() == null) throw new EntityNotFoundException("Image not founded");
        MediaTypeUrlResource resource =
                (MediaTypeUrlResource) storageService.loadAsResource(userFounded.getAvatar());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }


    @Operation(summary = "Obtiene todos los bajos favoritos de un usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado favoritos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BassResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                     "brand": "Sadowsky",
                                                     "model": "METROLINE 21-4 LIMITED EDITION WBT",
                                                     "frets": 21,
                                                     "image": "sadowsky-metroline-21-4-limited-edition-wbt.jpg",
                                                     "origin": "Alemania",
                                                     "builtYear": "2021",
                                                     "price": 3190.0,
                                                     "discount": 0.0,
                                                     "isAvailable": true,
                                                     "state": "NEW"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún favorito",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @GetMapping("/user/fav")
    public List<BassResponse> favList(@AuthenticationPrincipal User user) {
        return userService.favList(user.getId());
    }

    @Operation(summary = "Modifica una user")
    @Parameter(description = "El id del user que se quiera modificar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado correctamente un user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
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
                                                "createdAt": "17/02/2023 00:00:00"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar el user",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @PutMapping("/user/edit")
    public UserResponse editDetails(@AuthenticationPrincipal User loggedUser,
                                    @Valid @RequestPart("editUserDto") EditUserDto editUserDto,
                                    @RequestPart("file") MultipartFile file) {
        User edited = userService.editDetails(loggedUser, editUserDto, file);
        return UserResponse.fromUser(edited);
    }


    @Operation(summary = "Modifica una password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado correctamente la password",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "a520d2e5-16c1-44ce-a120-ed19c862d2bd",
                                                "username": "rogemb",
                                                "fullName": "Rogelio Mohigefer Barrera",
                                                "email": "rogelio@gmail.com",
                                                "avatar": "userDefault2_739400.png",
                                                "enabled": true,
                                                "createdAt": "17/02/2023 00:00:00"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar la password",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @PutMapping("/user/changepassword")
    public UserResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                       @AuthenticationPrincipal User loggedUser) {

        User modified = userService.editPassword(loggedUser, changePasswordRequest);
        return (UserResponse.fromUser(modified));
    }
    @Operation(summary = "Este método elimina un usuario localizado por su id")
    @ApiResponses(value={@ApiResponse(responseCode = "204", description = "User borrado con éxito",
            content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content)})
    @Parameter(description = "El id del usuario que se quiere eliminar", name = "id", required = true)
    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Este método elimina la cuenta del usuario autenticado")
    @ApiResponses(value={@ApiResponse(responseCode = "204", description = "Cuenta borrada con éxito",
            content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content)})
    @Parameter(description = "El id del usuario que se quiere eliminar", name = "id", required = true)
    @DeleteMapping("/user/deletemyaccount")
    public ResponseEntity<?> deleteMyAccount(@AuthenticationPrincipal User loggedUser) {
        userService.delete(loggedUser);
        return ResponseEntity.noContent().build();
    }


    // En nuestro caso, como un usuario no tiene acceso a la lista de usuarios ni sus datos, con cambiar el enabled a
    // FALSE y controlar el acceso de los ENABLED será suficiente. El usuario conserva su cuenta, pero por motivos de
    // políticas de la app el Admin puede restringir su acceso hasta nuevo aviso.

    @Operation(summary = "Este método banea el acceso de un usuario localizado por su id")
    @Parameter(description = "El id del usuario que se quiere banear", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha baneado el acceso del usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "a520d2e5-16c1-44ce-a120-ed19c862d2bd",
                                                "username": "rogemb",
                                                "fullName": "Roge Mohigefer",
                                                "email": "rogelio@gmail.com",
                                                "avatar": "userDefault.png",
                                                "enabled": false,
                                                "createdAt": "17/02/2023 00:00:00"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún user",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe el user que buscaba",
                    content = @Content),
    })
    @DeleteMapping("/admin/bann/{id}")
    public ResponseEntity<UserResponse> bannOneUser(@AuthenticationPrincipal User loggedUser,
                                                    @PathVariable UUID id,
                                                    @Valid @RequestBody BannUserDto bannUserDto) {
        UserResponse banned = userService.bannUser(id, bannUserDto);
        return ResponseEntity.ok(banned);
    }

}
