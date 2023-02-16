package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.model.Bajo;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;
/*
@Builder
public record BajoDto (UUID id, String productName, LocalDate createdAt, double price, boolean available) {
    public static BajoDto of(Bajo Bajo){
        return BajoDto.builder()
                .id(Bajo.getId())
                .productName(Bajo.getName())
                .createdAt(Bajo.getCreatedAt())
                .price(Bajo.getPrice())
                .available(Bajo.isAvalaible())
                .build();
    }
}
*/