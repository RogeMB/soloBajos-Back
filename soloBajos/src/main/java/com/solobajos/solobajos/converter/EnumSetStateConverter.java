package com.solobajos.solobajos.converter;

import com.solobajos.solobajos.model.BassState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

@Converter
public class EnumSetStateConverter implements AttributeConverter<EnumSet<BassState>, String> {
    private final String SEPARADOR = ", ";

    @Override
    public String convertToDatabaseColumn(EnumSet<BassState> attribute) {

        if (!attribute.isEmpty()) {
            return attribute.stream()
                    .map(BassState::name)
                    .collect(Collectors.joining(SEPARADOR));
        }
        return null;
    }

    @Override
    public EnumSet<BassState> convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            if (!dbData.isBlank()) {
                return Arrays.stream(dbData.split(SEPARADOR)) // Separamos la cadena en un array de String y lo convertimos en Stream
                        .map(BassState::valueOf) // Mapeamos cada cadena con su correspondiente valor de la enumeracion
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(BassState.class))); // Los recogemos en la colección correspondiente
            }
        }
        return EnumSet.noneOf(BassState.class);
    }
}
