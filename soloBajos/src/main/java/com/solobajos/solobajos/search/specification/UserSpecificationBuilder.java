package com.solobajos.solobajos.search.specification;

import com.solobajos.solobajos.model.User;
import com.solobajos.solobajos.search.util.SearchCriteria;

import java.util.List;

public class UserSpecificationBuilder extends GenericSpecificationBuilder<User> {
    public UserSpecificationBuilder(List<SearchCriteria> params) {
        super(params, User.class);
    }
}