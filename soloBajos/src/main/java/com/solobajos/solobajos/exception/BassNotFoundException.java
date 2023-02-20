package com.solobajos.solobajos.exception;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public class BassNotFoundException extends EntityNotFoundException {
    public BassNotFoundException(UUID id) {
        super(String.format("The bass with id %d could not be found", id));
    }
}
