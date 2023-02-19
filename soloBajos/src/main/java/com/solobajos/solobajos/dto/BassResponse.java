package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.model.Bass;
import com.solobajos.solobajos.model.BassState;
import lombok.Builder;

import java.time.Year;

@Builder
public record BassResponse (String brand,
                            String model,
                            int frets,
                            String image,
                            String origin,
                            Year builtYear,
                            double price,
                            double discount,
                            Boolean isAvailable,
                            BassState state) {

    public static BassResponse fromBass(Bass bass) {
        return BassResponse.builder()
                .brand(bass.getBrand())
                .model(bass.getModel())
                .frets(bass.getFrets())
                .image(bass.getImage())
                .origin(bass.getOrigin())
                .builtYear(bass.getBuiltYear())
                .price(bass.getPrice())
                .discount(bass.getDiscount())
                .isAvailable(bass.getIsAvailable())
                .state(bass.getState())
                .build();
    }
}


