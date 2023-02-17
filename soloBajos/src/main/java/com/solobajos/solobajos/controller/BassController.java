package com.solobajos.solobajos.controller;

/*
@RestController
@RequestMapping("/bajo/")
@RequiredArgsConstructor
public class BajoController {

    private final BajoService productService;

    @GetMapping("/")
    public ResponseEntity<PageDto<BajotDto>> getAllProducts(
            @RequestParam(value = "s", defaultValue = "") String search,
            @PageableDefault(size = 25, page = 0) Pageable pageable){

        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(search);
        PageDto<BajoDto> res = productService.search(params, pageable);

        if(res.getContent().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(res);
    }

}*/