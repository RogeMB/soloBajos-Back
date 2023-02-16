package com.solobajos.solobajos.service;

import com.solobajos.solobajos.model.Bajo;
import com.solobajos.solobajos.search.specification.BajoSpecificationBuilder;
import com.solobajos.solobajos.search.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
/*
@Service
@RequiredArgsConstructor
public class BajoService {

    private final Bajorepository bajoRepository;

    public PageDto<BajoDto> search(List<SearchCriteria> params, Pageable pageable){
        BajoSpecificationBuilder psBuilder = new BajoSpecificationBuilder(params);

        Specification<Bajo> spec = psBuilder.build();
        Page<BajoDto> pageBajoDto = bajoRepository.findAll(spec, pageable).map(BajoDto::of);

        return new PageDto<>(pageProductDto);
    }
}*/