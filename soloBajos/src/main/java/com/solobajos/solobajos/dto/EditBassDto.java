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
    @NotEmpty(message = "{createBassDto.brand.notempty}")
    @NotBlank(message = "{createBassDto.brand.notempty}")
    private String brand;
    @NotEmpty(message = "{createBassDto.model.notempty}")
    @NotBlank(message = "{createBassDto.model.notempty}")
    private String model;
    @NotEmpty(message = "{createBassDto.frets.notempty}")
    @NotBlank(message = "{createBassDto.frets.notempty}")
    private int frets;
    @NotEmpty(message = "{createBassDto.origin.notempty}")
    @NotBlank(message = "{createBassDto.origin.notempty}")
    private String origin;
    @NotEmpty(message = "{createBassDto.year.notempty}")
    @NotBlank(message = "{createBassDto.year.notempty}")
    private Year builtYear;
    @NotEmpty(message = "{createBassDto.price.notempty}")
    @NotBlank(message = "{createBassDto.price.notempty}")
    private double price;
    private double discount;
    private Boolean isAvailable;
    @NotEmpty(message = "{createBassDto.state.notempty}")
    @NotBlank(message = "{createBassDto.state.notempty}")
    private BassState state;
    @NotEmpty(message = "{createBassDto.state.notempty}")
    @NotBlank(message = "{createBassDto.state.notempty}")
    private UUID categoria_id;
}
