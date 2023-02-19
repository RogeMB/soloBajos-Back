package com.solobajos.solobajos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditCategoriaDto {
    @NotEmpty(message = "{categoriaDto.name.notempty}")
    private String name;

}