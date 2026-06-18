package com.library.management.exception;

public class LibraryException extends RuntimeException{

    private final int statusCode;

    public LibraryException(String message,int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
