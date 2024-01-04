package com.speer.notes.exception;

public class RateLimitExceededException extends RuntimeException{

    public String message;

    public RateLimitExceededException(String message){
        super(message);
        this.message = message;
    }

}
