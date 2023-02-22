package com.solobajos.solobajos.service;


import com.solobajos.solobajos.dto.*;
import com.solobajos.solobajos.exception.BassNotFoundException;
import com.solobajos.solobajos.exception.EmptyBassListException;
import com.solobajos.solobajos.exception.UserNotFoundException;
import com.solobajos.solobajos.model.Bass;
import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.repository.BassRepository;
import com.solobajos.solobajos.repository.UserRepository;
import com.solobajos.solobajos.search.specification.BassSpecificationBuilder;
import com.solobajos.solobajos.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BassService {

    private final BassRepository bassRepository;
    private final StorageService storageService;

    private final CategoriaService categoriaService;
    private final UserRepository userRepository;

    public PageDto<BassResponse> findAllSearch(List<SearchCriteria> params, Pageable pageable){
        BassSpecificationBuilder bassSpecificationBuilderBuilder = new BassSpecificationBuilder(params);

        Specification<Bass> spec = bassSpecificationBuilderBuilder.build();
        Page<BassResponse> pageBassDto = bassRepository.findAll(spec, pageable).map(BassResponse::fromBass);

        if(pageBassDto.isEmpty())
            throw new EmptyBassListException();

        return new PageDto<>(pageBassDto);
    }

    public Bass findById(UUID id) {
        return bassRepository.findById(id)
                .orElseThrow(() -> new BassNotFoundException(id));
    }

    @Transactional
    public Bass save(CreateBassDto createBassDto) {
        return bassRepository.save(
                Bass.builder()
                        .brand(createBassDto.brand())
                        .model(createBassDto.model())
                        .frets(createBassDto.frets())
                        .origin(createBassDto.origin())
                        .builtYear(createBassDto.builtYear())
                        .price(createBassDto.price())
                        .discount(createBassDto.discount())
                        .state(createBassDto.state())
                        .isAvailable(createBassDto.isAvailable())
                        .categoria(categoriaService.findById(createBassDto.categoria_id()))
                        .build());
    }


    public Bass edit(UUID id, EditBassDto editBassDto, MultipartFile file) {
        String filename = storageService.store(file);
        return bassRepository.findById(id).map(bass -> {
            bass.setBrand(editBassDto.getBrand());
            bass.setModel(editBassDto.getModel());
            bass.setFrets(editBassDto.getFrets());
            bass.setImage(filename);
            bass.setOrigin(editBassDto.getOrigin());
            bass.setPrice(editBassDto.getPrice());
            bass.setDiscount(editBassDto.getDiscount());
            bass.setBuiltYear(editBassDto.getBuiltYear());
            bass.setIsAvailable(editBassDto.getIsAvailable());
            bass.setCategoria(categoriaService.findById(editBassDto.getCategoria_id()));
            return bassRepository.save(bass);
        }).orElseThrow(() -> new BassNotFoundException(id));
    }

    public void delete(UUID id) {
        if(bassRepository.existsById(id))
            bassRepository.deleteById(id);
        else {
            throw new BassNotFoundException(id);
        }
    }
    @Transactional
    public Bass makeFav(User user, Bass bass) {
        User userFound = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));

        if(userRepository.findFirstFav(userFound.getId(), bass.getId())){
            userFound.removeFromBassListFav(bass);
            userRepository.save(userFound);
        }else {
            userFound.addToBassFavList(bass);
            userRepository.save(userFound);
        }
        return bassRepository.save(bass);
    }


}