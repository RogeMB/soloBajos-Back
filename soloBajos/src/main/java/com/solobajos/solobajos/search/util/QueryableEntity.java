package com.solobajos.solobajos.search.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public interface QueryableEntity {

    static boolean checkQueryParam(Class clazz, String fieldName) { // String hiddenFields

        //List<String> hiddenFieldsList  = (hiddenFields.contains(",")) ? Arrays.asList(hiddenFields.split(",")) : List.of(hiddenFields);

        //List<String> queryableFields = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).filter(f -> !hiddenFieldsList.contains(f)).toList();

        //return queryableFields.stream().anyMatch(n -> n.equalsIgnoreCase(fieldName));

        return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).anyMatch(name -> name.equalsIgnoreCase(fieldName));
    }
}