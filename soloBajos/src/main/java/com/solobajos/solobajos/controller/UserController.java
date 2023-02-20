package com.solobajos.solobajos.controller;

import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.exception.PasswordNotMathException;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.search.util.SearchCriteria;
import com.solobajos.solobajos.search.util.SearchCriteriaExtractor;
import com.solobajos.solobajos.security.jwt.JwtProvider;
import com.solobajos.solobajos.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Solo-Bajos API"))
@Tag(name = "User", description = "Esta clase implementa Restcontrollers para la entidad user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtProvider jwtProvider;

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

    @PutMapping("/user/edit")
    public UserResponse editDetails(@AuthenticationPrincipal User loggedUser,
                                    @Valid @RequestPart EditUserDto editUserDto, @RequestPart MultipartFile file) {
        User edited = userService.editDetails(loggedUser, editUserDto, file);
        return UserResponse.fromUser(edited);
    }

    @PutMapping("/user/changePassword")
    public UserResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                       @AuthenticationPrincipal User loggedUser) throws PasswordNotMathException {

        User modified = userService.editPassword(loggedUser, changePasswordRequest);
        return (UserResponse.fromUser(modified));
    }

    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
