package com.solobajos.solobajos.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyCategoriaListException extends EntityNotFoundException {
    public EmptyCategoriaListException() {
        super("No categoria were found with the search criteria");
    }
}
