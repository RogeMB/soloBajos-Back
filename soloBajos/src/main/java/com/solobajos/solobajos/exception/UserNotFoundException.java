package com.solobajos.solobajos.exception;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(UUID id) {
        super(String.format("The user with id %d could not be found", id));
    }
}
