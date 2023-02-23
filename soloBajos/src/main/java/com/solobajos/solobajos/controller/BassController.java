package com.solobajos.solobajos.controller;


import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.model.Bass;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.search.util.SearchCriteria;
import com.solobajos.solobajos.search.util.SearchCriteriaExtractor;
import com.solobajos.solobajos.service.BassService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title ="Solo-Bajos API"))
@Tag(name = "Bass", description = "Esta clase implementa Restcontrollers para la entidad Bass")
public class BassController {

    private final BassService bassService;
    private final StorageService storageService;


    @Operation(summary = "Obtiene todos los bajos paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado bajos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PageDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "content": [
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
                                                    },
                                                    {
                                                        "brand": "Sadowsky",
                                                        "model": "METROEXPRESS 21 VINTAGE J/J BASS MN BLK",
                                                        "frets": 21,
                                                        "image": "sadowsky-metroexpress-21-fret-vintage-mn-blk.jpg",
                                                        "origin": "China",
                                                        "builtYear": "2021",
                                                        "price": 777.0,
                                                        "discount": 0.0,
                                                        "isAvailable": true,
                                                        "state": "NEW"
                                                    },
                                                    {
                                                        "brand": "Sadowsky",
                                                        "model": "METROEXPRESS 21 VINTAGE J/J BASS MN CAR",
                                                        "frets": 21,
                                                        "image": "sadowsky-metroexpress-21-vintage-jj-bass-mn-car.jpg",
                                                        "origin": "China",
                                                        "builtYear": "2021",
                                                        "price": 777.0,
                                                        "discount": 0.0,
                                                        "isAvailable": true,
                                                        "state": "NEW"
                                                    },
                                                    {
                                                        "brand": "Musicman",
                                                        "model": "Bongo",
                                                        "frets": 24,
                                                        "image": null,
                                                        "origin": "EEUU",
                                                        "builtYear": "2022",
                                                        "price": 2400.0,
                                                        "discount": 0.0,
                                                        "isAvailable": true,
                                                        "state": "NEW"
                                                    }
                                                ],
                                                "last": true,
                                                "first": true,
                                                "totalPages": 1,
                                                "totalElements": 4,
                                                "currentPage": 0
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún bajo",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe el bajo que buscaba",
                    content = @Content),
    })
    @GetMapping("/bass")
    public PageDto<BassResponse> getAllBasses(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault(size = 10, page = 0) Pageable pageable){

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        return bassService.findAllSearch(params, pageable);
    }


    @Operation(summary = "Obtiene un bajo por su id")
    @Parameter(description = "El id del bajo que se quiere buscar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el bajo",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BassResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
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
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún bajo",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe el bajo que buscaba",
                    content = @Content),
    })
    @GetMapping("/bass/{id}")
    public BassResponse getById(@PathVariable UUID id) {
        return BassResponse.fromBass(bassService.findById(id));
    }


    @Operation(summary = "Obtiene la imagen de un bajo por su id")
    @Parameter(description = "El id del bajo que se quiere buscar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la imagen del bajo",
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
                    description = "No se ha encontrado ningún bajo",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No existe el bajo que buscaba",
                    content = @Content),
    })
    @GetMapping("/bass/image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable UUID id){
        Bass bass = bassService.findById(id);
        if(bass.getImage() == null) throw new EntityNotFoundException("Image not found");
        MediaTypeUrlResource resource =
                (MediaTypeUrlResource) storageService.loadAsResource(bass.getImage());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

    @Operation(summary = "Este método crea un nuevo bajo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BassResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                          "brand": "Musicman",
                                          "model": "Bongo",
                                          "frets": "24",
                                          "origin": "EEUU",
                                          "builtYear": "2022",
                                          "price": "2400.00",
                                          "discount": "0.0",
                                          "isAvailable": true,
                                          "state": "NEW",
                                          "categoria_id": "746388fe-83eb-43f1-8851-9cab3008c0f2"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido correctamente los datos del nuevo bajo",
                    content = @Content),
    })
    @PostMapping("/admin/bass")
    public ResponseEntity<BassResponse> createBass(@Valid @RequestBody CreateBassDto createBassDto) {
        Bass created = bassService.save(createBassDto);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(createdURI).body(BassResponse.fromBass(created));
    }

    @Operation(summary = "Este método crea un nuevo bajo")
    @Parameter(description = "El id del bajo que se quiere guardar de favorito", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BassResponse.class)),
                            examples = @ExampleObject(value = """
                                    {
                                          "brand": "Musicman",
                                          "model": "Bongo",
                                          "frets": "24",
                                          "origin": "EEUU",
                                          "builtYear": "2022",
                                          "price": "2400.00",
                                          "discount": "0.0",
                                          "isAvailable": true,
                                          "state": "NEW",
                                          "categoria_id": "746388fe-83eb-43f1-8851-9cab3008c0f2"
                                    }
                                    """)) }),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido correctamente el id",
                    content = @Content),
    })
    @PostMapping("/bass/fav/{id}")
    public ResponseEntity<BassResponse> favBass(@PathVariable UUID id,
                                                @AuthenticationPrincipal User user) {
        Bass bass = bassService.findById(id);
        Bass fav = bassService.makeFav(user, bass);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bass.getId()).toUri();

        return ResponseEntity.created(createdURI).body(BassResponse.fromBass(bass));
    }


    @Operation(summary = "Modifica un bajo por su id")
    @Parameter(description = "El id del bajo que se quiere modificar", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado correctamente un bajo ",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BassResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "brand": "Musicman",
                                                "model": "Stingray",
                                                "frets": 21,
                                                "image": "stringray1 (2)_589971.png",
                                                "origin": "EEUU",
                                                "builtYear": "2022",
                                                "price": 1900.0,
                                                "discount": 0.0,
                                                "isAvailable": true,
                                                "state": "NEW"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar el bajo",
                    content = @Content),
    })
    @PutMapping("/admin/bass/{id}")
    public BassResponse editBass(@PathVariable UUID id,
                                 @RequestPart("file") MultipartFile file,
                                 @Valid  @RequestPart("editBassDto") EditBassDto editBassDto){
        Bass edited = bassService.edit(id, editBassDto, file);
        return BassResponse.fromBass(edited);
    }


    @Operation(summary = "Este método elimina un bajo localizado por su id")
    @ApiResponse(responseCode = "204", description = "Bajo borrado con éxito",
            content = @Content)
    @Parameter(description = "El id del bajo que se quiere eliminar", name = "id", required = true)
    @DeleteMapping("/admin/bass/{id}")
    public ResponseEntity<?> deleteBass(@PathVariable UUID id) {
        bassService.delete(id);
        return ResponseEntity.noContent().build();
    }

}