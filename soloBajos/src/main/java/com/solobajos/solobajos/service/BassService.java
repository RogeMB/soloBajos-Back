package com.solobajos.solobajos.service;

/*
@Service
@RequiredArgsConstructor
public class BajoService {

    private final Bajorepository bajoRepository;

    public PageDto<BajoDto> search(List<SearchCriteria> params, Pageable pageable){
        BassSpecificationBuilder psBuilder = new BassSpecificationBuilder(params);

        Specification<Bass> spec = psBuilder.build();
        Page<BajoDto> pageBajoDto = bajoRepository.findAll(spec, pageable).map(BajoDto::of);

        return new PageDto<>(pageProductDto);
    }
}*/