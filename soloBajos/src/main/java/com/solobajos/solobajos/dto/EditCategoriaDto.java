package com.solobajos.solobajos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditCategoriaDto {
    @NotEmpty(message = "{createCategoriaDto.name.notempty}")
    @NotBlank(message = "{createCategoriaDto.name.notblank}")
    private String name;

}