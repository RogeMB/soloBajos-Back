package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.model.BassState;
import com.solobajos.solobajos.model.Categoria;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Year;
import java.util.UUID;

@Builder
public record CreateBassDto (@NotEmpty(message = "{createBassDto.brand.notempty}")
                             @NotBlank(message = "{createBassDto.brand.notempty}") String brand,
                             @NotEmpty(message = "{createBassDto.model.notempty}")
                             @NotBlank(message = "{createBassDto.model.notempty}") String model,
                             @NotEmpty(message = "{createBassDto.frets.notempty}")
                             @NotBlank(message = "{createBassDto.frets.notempty}") int frets,
                             @NotEmpty(message = "{createBassDto.origin.notempty}")
                             @NotBlank(message = "{createBassDto.origin.notempty}") String origin,
                             @NotEmpty(message = "{createBassDto.year.notempty}")
                             @NotBlank(message = "{createBassDto.year.notempty}") Year builtYear,
                             @NotEmpty(message = "{createBassDto.price.notempty}")
                             @NotBlank(message = "{createBassDto.price.notempty}") double price,
                             double discount,
                             Boolean isAvailable,
                             @NotEmpty(message = "{createBassDto.state.notempty}")
                             @NotBlank(message = "{createBassDto.state.notempty}")
                             BassState state,
                             @NotEmpty(message = "{createBassDto.categoria.notempty}")
                             @NotBlank(message = "{createBassDto.categoria.notempty}")
                             UUID categoria_id) {
}
