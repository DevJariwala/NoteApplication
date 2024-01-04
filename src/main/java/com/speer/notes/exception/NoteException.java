package com.speer.notes.exception;

public class NoteException extends RuntimeException{

    public String message;

    public NoteException(String message){
        super(message);
        this.message = message;
    }

}
