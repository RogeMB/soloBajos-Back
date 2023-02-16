package com.solobajos.solobajos.controller;

import com.solobajos.solobajos.search.util.SearchCriteria;
import com.solobajos.solobajos.search.util.SearchCriteriaExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;
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