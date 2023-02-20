package com.solobajos.solobajos.dto;

import com.solobajos.solobajos.model.Categoria;
import lombok.Builder;

@Builder
public record CategoriaResponse (String name, String image){
    public static CategoriaResponse fromCategoria(Categoria categoria) {
        return CategoriaResponse.builder()
                .name(categoria.getName())
                .image(categoria.getImage())
                .build();
    }
}
