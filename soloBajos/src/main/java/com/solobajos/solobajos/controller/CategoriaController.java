package com.solobajos.solobajos.controller;


import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.model.Categoria;
import com.solobajos.solobajos.service.CategoriaService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Solo-Bajos API"))
@Tag(name = "Categoria", description = "Esta clase implementa Restcontrollers para la entidad Categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/categoria")
    public List<CategoriaResponse> getAllCategorias() {
        return categoriaService.findAll().stream().map(CategoriaResponse::fromCategoria).toList();
    }


    @GetMapping("/categoria/{id}")
    public CategoriaResponse getById(@PathVariable UUID id) {
        return CategoriaResponse.fromCategoria(categoriaService.findById(id));
    }

    @PostMapping("/admin/categoria")
    public ResponseEntity<CategoriaResponse> createCategoria(@Valid @RequestBody CreateCategoriaDto createCategoriaDto) {
        Categoria created = categoriaService.save(createCategoriaDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(createdURI).body(CategoriaResponse.fromCategoria(created));
    }


    @PutMapping("/admin/categoria/{id}")
    public CategoriaResponse editCategoria(@PathVariable UUID id, @Valid
                                           @RequestPart("file") MultipartFile file,
                                           @RequestPart("editCategoriaDto")EditCategoriaDto editCategoriaDto){
        Categoria edited = categoriaService.edit(id, editCategoriaDto, file);
        return CategoriaResponse.fromCategoria(edited);
    }


    @DeleteMapping("/admin/categoria/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable UUID id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
