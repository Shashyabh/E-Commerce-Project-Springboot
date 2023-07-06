package com.store.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super("Resource not found");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
