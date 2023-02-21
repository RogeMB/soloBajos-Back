package com.solobajos.solobajos.controller;


import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.model.Bass;
import com.solobajos.solobajos.model.Categoria;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.search.util.SearchCriteria;
import com.solobajos.solobajos.search.util.SearchCriteriaExtractor;
import com.solobajos.solobajos.service.BassService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Solo-Bajos API"))
@Tag(name = "Bass", description = "Esta clase implementa Restcontrollers para la entidad Bass")
public class BassController {

    private final BassService bassService;

    @GetMapping("/bass")
    public PageDto<BassResponse> getAllBasses(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable){

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        return bassService.findAllSearch(params, pageable);
    }

    @GetMapping("/bass/{id}")
    public BassResponse getById(@PathVariable UUID id) {
        return BassResponse.fromBass(bassService.findById(id));
    }
    @GetMapping("/bass/fav")
    public List<BassResponse> favList(@AuthenticationPrincipal User user) {
        return bassService.favList(user.getId());
    }
    @PostMapping("/admin/bass")
    public ResponseEntity<BassResponse> createCategoria(@Valid @RequestBody CreateBassDto createBassDto) {
        Bass created = bassService.save(createBassDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(createdURI).body(BassResponse.fromBass(created));
    }


    @PostMapping("/bass/fav/{id}")
    public BassResponse favBass(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        Bass bass = bassService.findById(id);
        Bass fav = bassService.makeFav(user, bass);

        return BassResponse.fromBass(bass);
    }
    @PutMapping("/admin/bass/{id}")
    public BassResponse editBass(@PathVariable UUID id, @Valid @RequestPart("file") MultipartFile file,
                                 @RequestPart("editBassDto") EditBassDto editBassDto){
        Bass edited = bassService.edit(id, editBassDto, file);
        return BassResponse.fromBass(edited);
    }



    @DeleteMapping("/admin/bass/{id}")
    public ResponseEntity<?> deleteBass(@PathVariable UUID id) {
        bassService.delete(id);
        return ResponseEntity.noContent().build();
    }

}