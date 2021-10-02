package com.nuitblanche.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CUserNotFoundException extends RuntimeException{

    public CUserNotFoundException(String message){
        super(message);
    }

    public CUserNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
