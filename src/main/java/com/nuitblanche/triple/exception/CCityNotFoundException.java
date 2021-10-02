package com.nuitblanche.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CCityNotFoundException extends RuntimeException{

    public CCityNotFoundException(String message){
        super(message);
    }

    public CCityNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
