package com.solobajos.solobajos.search.specification;

import com.solobajos.solobajos.search.util.QueryableEntity;
import com.solobajos.solobajos.search.util.SearchCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
public class GenericSpecificationBuilder<T> {
    private List<SearchCriteria> params;
    private Class type;
    private String hiddenFields;
    public Specification<T> build() {
        List<SearchCriteria> checkedParams = params.stream()
                .filter(p -> QueryableEntity.checkQueryParam(type, p.getKey(), hiddenFields))
                .toList();

        if (checkedParams.isEmpty()) {
            return null;
        }

        Specification<T> result = new GenericSpecification<T>(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = result.and(new GenericSpecification<T>(params.get(i)));
        }
        return result;
    }
}