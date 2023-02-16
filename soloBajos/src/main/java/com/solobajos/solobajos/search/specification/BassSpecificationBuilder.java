package com.solobajos.solobajos.search.specification;

import com.solobajos.solobajos.model.Bass;
import com.solobajos.solobajos.search.util.SearchCriteria;

import java.util.List;

public class BassSpecificationBuilder extends GenericSpecificationBuilder<Bass>{
    public BassSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Bass.class, Bass.hiddenFields);
    }
}
