package com.solobajos.solobajos.exception;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public class CategoriaNotFoundException extends EntityNotFoundException {
    public CategoriaNotFoundException(UUID id) {
        super(String.format("The categoria with id %d could not be found", id));
    }
}
