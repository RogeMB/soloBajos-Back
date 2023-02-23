package com.solobajos.solobajos.controller;


import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.model.Categoria;
import com.solobajos.solobajos.service.CategoriaService;
import com.solobajos.solobajos.service.StorageService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
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
    private final StorageService storageService;

    @Operation(summary = "Obtiene todos las categorías paginadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado categorías",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoriaResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "name": "Bajos de 4 Cuerdas",
                                                    "image": "categoriaDefault.png"
                                                },
                                                {
                                                    "name": "Bajos de 5 Cuerdas",
                                                    "image": "categoriaDefault.png"
                                                },
                                                {
                                                    "name": "Bajos de 6 Cuerdas",
                                                    "image": "categoriaDefault.png"
                                                },
                                                {
                                                    "name": "Bajos fretless",
                                                    "image": "categoriaDefault.png"
                                                },
                                                {
                                                    "name": "Bajos zurdos",
                                                    "image": "categoriaDefault.png"
                                                },
                                                {
                                                    "name": "Bajos acÃºsticos",
                                                    "image": "categoriaDefault.png"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna categoría",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @GetMapping("/categoria")
    public List<CategoriaResponse> getAllCategorias() {
        return categoriaService.findAll().stream().map(CategoriaResponse::fromCategoria).toList();
    }


    @Operation(summary = "Obtiene una categoría por su id")
    @Parameter(description = "El id de la categoría que se quiere buscar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la categoría",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoriaResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "name": "Bajos de 4 Cuerdas",
                                                "image": "categoriaDefault.png"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna categoría",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe la categoría que buscaba",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
    })
    @GetMapping("/categoria/{id}")
    public CategoriaResponse getById(@PathVariable UUID id) {
        return CategoriaResponse.fromCategoria(categoriaService.findById(id));
    }


    @Operation(summary = "Obtiene la imagen de una categoría por su id")
    @Parameter(description = "El id de la categoría que se quiera buscar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la imagen de la categoría",
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
                    description = "No se ha encontrado ninguna categoría",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe la categoría que buscaba",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "415",
                    description = "Formato no permitido",
                    content = @Content),
    })
    @GetMapping("/categoria/image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable UUID id){
        Categoria categoria = categoriaService.findById(id);
        if(categoria.getImage() == null) throw new EntityNotFoundException("Image not found");
        MediaTypeUrlResource resource =
                (MediaTypeUrlResource) storageService.loadAsResource(categoria.getImage());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

    @Operation(summary = "Este método crea una nueva categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoriaResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                        "name": "Bajos heavys",
                                        "image": "categoriaDefault.png"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido correctamente los datos de la categoría",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content),
    })
    @PostMapping("/admin/categoria")
    public ResponseEntity<CategoriaResponse> createCategoria(@Valid @RequestBody CreateCategoriaDto createCategoriaDto) {
        Categoria created = categoriaService.save(createCategoriaDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(createdURI).body(CategoriaResponse.fromCategoria(created));
    }

    @Operation(summary = "Modifica una categoría")
    @Parameter(description = "El id de la categoría que se quiera modificar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado correctamente una categoría ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoriaResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "name": "Bajos Precision",
                                                "image": "categoriaDefault2_594667.png"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar la categoría",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content),
    })
    @PutMapping("/admin/categoria/{id}")
    public CategoriaResponse editCategoria(@PathVariable UUID id,
                                           @RequestPart("file") MultipartFile file,
                                           @Valid @RequestPart("editCategoriaDto")EditCategoriaDto editCategoriaDto){
        Categoria edited = categoriaService.edit(id, editCategoriaDto, file);
        return CategoriaResponse.fromCategoria(edited);
    }


    @Operation(summary = "Este método elimina una categoría localizada por su id")
    @ApiResponses(value={@ApiResponse(responseCode = "204", description = "Categoría borrada con éxito",
            content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "No está autorizado",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acción prohibida para este user",
                    content = @Content)})
    @Parameter(description = "El id de la categoría que se quiere eliminar", name = "id", required = true)
    @DeleteMapping("/admin/categoria/{id}")
    public ResponseEntity<?> deleteCategoria(@PathVariable UUID id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
