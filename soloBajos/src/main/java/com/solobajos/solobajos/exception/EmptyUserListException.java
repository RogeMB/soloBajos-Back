package com.solobajos.solobajos.exception;


import javax.persistence.EntityNotFoundException;

public class EmptyUserListException extends EntityNotFoundException {
    public EmptyUserListException() {
        super("No user were found with the search criteria");
    }
}
