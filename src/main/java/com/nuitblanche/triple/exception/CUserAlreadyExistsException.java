package com.nuitblanche.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CUserAlreadyExistsException extends RuntimeException{

    public CUserAlreadyExistsException(String message){
        super(message);
    }

    public CUserAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
