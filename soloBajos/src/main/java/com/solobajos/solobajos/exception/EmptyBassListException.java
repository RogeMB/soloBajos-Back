package com.solobajos.solobajos.exception;

import javax.persistence.EntityNotFoundException;

public class EmptyBassListException extends EntityNotFoundException {
    public EmptyBassListException() {
        super("No bass were found with the search criteria");
    }
}