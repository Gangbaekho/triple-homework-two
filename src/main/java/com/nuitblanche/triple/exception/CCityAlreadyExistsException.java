package com.nuitblanche.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CCityAlreadyExistsException extends RuntimeException{

    public CCityAlreadyExistsException(String message){
        super(message);
    }

    public CCityAlreadyExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
