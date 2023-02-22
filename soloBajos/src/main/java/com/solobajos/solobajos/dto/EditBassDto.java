package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.model.BassState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditBassDto {
    @NotEmpty(message = "{createBassDto.brand.notempty}")
    @NotBlank(message = "{createBassDto.brand.notempty}")
    private String brand;
    @NotEmpty(message = "{createBassDto.model.notempty}")
    @NotBlank(message = "{createBassDto.model.notempty}")
    private String model;
    @Min(value = 16, message = "{createBassDto.price.min}")
    private int frets;
    @NotEmpty(message = "{createBassDto.origin.notempty}")
    @NotBlank(message = "{createBassDto.origin.notempty}")
    private String origin;
    @NotNull(message = "{createBassDto.year.notnull}")
    private Year builtYear;
    @Min(value = 0, message = "{createBassDto.price.min}")
    private double price;
    @Min(value = 0, message = "{createBassDto.discount.min}")
    private double discount;
    private Boolean isAvailable;
    @NotNull(message = "{createBassDto.state.notnull}")
    private BassState state;
    @NotNull(message = "{createBassDto.categoria.notnull}")
    private UUID categoria_id;
}
