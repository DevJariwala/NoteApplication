package com.speer.notes.exception;

public class UserAlreadyExistException extends NoteException {

    public UserAlreadyExistException(String message) {
        super(message);
    }

}