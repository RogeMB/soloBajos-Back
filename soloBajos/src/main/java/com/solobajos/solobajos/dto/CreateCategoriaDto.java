package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.validation.annotation.FieldsValueMatch;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
public record CreateCategoriaDto (@NotEmpty(message = "{createCategoriaDto.name.notempty}")
                                  @NotBlank(message = "{createCategoriaDto.name.notblank}")
                                  String name) {

}

