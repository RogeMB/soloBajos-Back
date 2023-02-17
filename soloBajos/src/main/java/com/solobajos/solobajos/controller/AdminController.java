package com.solobajos.solobajos.controller;

import com.solobajos.solobajos.dto.PageDto;
import com.solobajos.solobajos.dto.UserResponse;
import com.solobajos.solobajos.repository.UserRepository;
import com.solobajos.solobajos.search.util.SearchCriteria;
import com.solobajos.solobajos.search.util.SearchCriteriaExtractor;
import com.solobajos.solobajos.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/")
@OpenAPIDefinition(info = @Info(title ="Solo-Bajos API"))
@Tag(name = "Admin", description = "Esta clase implementa Restcontrollers para una entidad Admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public List<PageDto<UserResponse>> getAllUsers(
            @RequestParam(value = "s", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable){

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<UserResponse> res = userService.findAllSearch(params, pageable);

        if(res.getContent().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return res;
    }


    @GetMapping("/auth/users")
    public List<UserResponse> getAll() {
        return userService.findAllSearch().stream().map(UserResponse::fromUser).toList();
    }

}

