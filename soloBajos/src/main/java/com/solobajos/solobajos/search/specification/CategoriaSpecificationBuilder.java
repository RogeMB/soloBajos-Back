package com.solobajos.solobajos.search.specification;

import com.solobajos.solobajos.model.Categoria;
import com.solobajos.solobajos.search.util.SearchCriteria;

import java.util.List;

public class CategoriaSpecificationBuilder extends GenericSpecificationBuilder<Categoria> {
    public CategoriaSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Categoria.class);}
}
