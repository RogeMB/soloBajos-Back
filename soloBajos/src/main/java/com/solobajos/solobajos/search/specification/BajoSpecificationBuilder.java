package com.solobajos.solobajos.search.specification;

import com.solobajos.solobajos.model.Bajo;
import com.solobajos.solobajos.search.util.SearchCriteria;

import java.util.List;

public class BajoSpecificationBuilder extends GenericSpecificationBuilder<Bajo>{
    public BajoSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Bajo.class, Bajo.hiddenFields);
    }
}
