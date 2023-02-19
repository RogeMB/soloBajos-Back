package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.model.BassState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Year;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditBassDto {
    @NotEmpty(message = "{BassDto.brand.notempty}")
    @NotBlank(message = "{BassDto.brand.notempty}")
    private String brand;
    @NotEmpty(message = "{BassDto.model.notempty}")
    @NotBlank(message = "{BassDto.model.notempty}")
    private String model;
    @NotEmpty(message = "{BassDto.frets.notempty}")
    @NotBlank(message = "{BassDto.frets.notempty}")
    private int frets;
    @NotEmpty(message = "{BassDto.origin.notempty}")
    @NotBlank(message = "{BassDto.origin.notempty}")
    private String origin;
    @NotEmpty(message = "{BassDto.year.notempty}")
    @NotBlank(message = "{BassDto.year.notempty}")
    private Year builtYear;
    @NotEmpty(message = "{BassDto.price.notempty}")
    @NotBlank(message = "{BassDto.price.notempty}")
    private double price;
    private double discount;
    private Boolean isAvailable;
    @NotEmpty(message = "{BassDto.state.notempty}")
    @NotBlank(message = "{BassDto.state.notempty}")
    private BassState state;
    @NotEmpty(message = "{BassDto.state.notempty}")
    @NotBlank(message = "{BassDto.state.notempty}")
    private UUID categoria_id;
}
