package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.model.BassState;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.UUID;

@Builder
public record CreateBassDto (@NotEmpty(message = "{createBassDto.brand.notempty}")
                             @NotBlank(message = "{createBassDto.brand.notempty}") String brand,
                             @NotEmpty(message = "{createBassDto.model.notempty}")
                             @NotBlank(message = "{createBassDto.model.notempty}") String model,
                             @Min(value = 16, message = "{createBassDto.price.min}")
                             int frets,
                             @NotEmpty(message = "{createBassDto.origin.notempty}")
                             @NotBlank(message = "{createBassDto.origin.notempty}")
                             String origin,
                             @NotNull(message = "{createBassDto.year.notnull}")
                             Year builtYear,
                             @Min(value = 0, message = "{createBassDto.price.min}")
                             double price,
                             @Min(value = 0, message = "{createBassDto.discount.min}")
                             double discount,
                             Boolean isAvailable,
                             @NotNull(message = "{createBassDto.state.notnull}")
                             BassState state,
                             @NotNull(message = "{createBassDto.categoria.notnull}")
                             UUID categoria_id) {
}
