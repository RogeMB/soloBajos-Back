package com.solobajos.solobajos.controller;

import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.search.util.SearchCriteria;
import com.solobajos.solobajos.search.util.SearchCriteriaExtractor;
import com.solobajos.solobajos.service.StorageService;
import com.solobajos.solobajos.service.UserService;
import com.solobajos.solobajos.utils.MediaTypeUrlResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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

    @GetMapping("/admin/user")
    public PageDto<UserResponse> getAllUsers(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable){

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        return userService.findAllSearch(params, pageable);
    }

    @GetMapping("/admin/user/{id}")
    public UserResponse getById(@PathVariable UUID id) {
        return UserResponse.fromUser(userService.findById(id));
    }

    @GetMapping("/user/profile")
    public UserResponse getProfile(@AuthenticationPrincipal User loggedUser) {
        return UserResponse.fromUser(loggedUser);
    }

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

    @GetMapping("/user/fav")
    public List<BassResponse> favList(@AuthenticationPrincipal User user) {
        return userService.favList(user.getId());
    }

    @PutMapping("/user/edit")
    public UserResponse editDetails(@AuthenticationPrincipal User loggedUser,
                                    @Valid @RequestPart("editUserDto") EditUserDto editUserDto,
                                    @RequestPart("file") MultipartFile file) {
        User edited = userService.editDetails(loggedUser, editUserDto, file);
        return UserResponse.fromUser(edited);
    }

    @PutMapping("/user/changepassword")
    public UserResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                       @AuthenticationPrincipal User loggedUser) {

        User modified = userService.editPassword(loggedUser, changePasswordRequest);
        return (UserResponse.fromUser(modified));
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/deletemyaccount")
    public ResponseEntity<?> deleteMyAccount(@AuthenticationPrincipal User loggedUser) {
        userService.delete(loggedUser);
        return ResponseEntity.noContent().build();
    }

    // En nuestro caso, como un usuario no tiene acceso a la lista de usuarios ni sus datos, con cambiar el enabled a
    // FALSE y controlar el acceso de los ENABLED será suficiente. El usuario conserva su cuenta, pero por motivos de
    // políticas de la app el Admin puede restringir su acceso hasta nuevo aviso.
    @DeleteMapping("/admin/bann/{id}")
    public ResponseEntity<UserResponse> bannOneUser(@AuthenticationPrincipal User loggedUser,
                                                    @PathVariable UUID id,
                                                    @Valid @RequestBody BannUserDto bannUserDto) {
        UserResponse banned = userService.bannUser(id, bannUserDto);
        return ResponseEntity.ok(banned);
    }

}
