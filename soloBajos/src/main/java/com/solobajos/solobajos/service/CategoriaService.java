package com.solobajos.solobajos.service;

import com.solobajos.solobajos.dto.CreateCategoriaDto;
import com.solobajos.solobajos.dto.EditCategoriaDto;
import com.solobajos.solobajos.exception.CategoriaNotFoundException;
import com.solobajos.solobajos.exception.EmptyCategoriaListException;
import com.solobajos.solobajos.model.Categoria;
import com.solobajos.solobajos.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final StorageService storageService;

   /* public PageDto<CategoriaResponse> findAllSearch(List<SearchCriteria> params, Pageable pageable){
        CategoriaSpecificationBuilder categoriaSpecificationBuilderBuilder = new CategoriaSpecificationBuilder(params);

        Specification<Categoria> spec = categoriaSpecificationBuilderBuilder.build();
        Page<CategoriaResponse> pageCategoriaDto = categoriaRepository.findAll(spec, pageable).map(CategoriaResponse::fromCategoria);

        if(pageCategoriaDto.isEmpty())
            throw new EmptyCategoriaListException();

        return new PageDto<>(pageCategoriaDto);
    }*/

    public List<Categoria> findAll() {
        List<Categoria> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty()){
            throw new EmptyCategoriaListException();
        }
        return categorias;
    }


    public Categoria findById(UUID id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @Transactional
    public Categoria save(CreateCategoriaDto createCategoriaDto) {
        return categoriaRepository.save(
                Categoria.builder()
                        .name(createCategoriaDto.name())
                        .build());
    }

    public Categoria edit(UUID id, EditCategoriaDto editCategoriaDto, MultipartFile file) {
        String filename = storageService.store(file);
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setName(editCategoriaDto.getName());
            categoria.setImage(filename);
            return categoriaRepository.save(categoria);
        }).orElseThrow(() -> new CategoriaNotFoundException(id));

    }

    public void delete(UUID id) {
        if(categoriaRepository.existsById(id))
            categoriaRepository.deleteById(id);
        else {
            throw new CategoriaNotFoundException(id);
        }
    }

}
