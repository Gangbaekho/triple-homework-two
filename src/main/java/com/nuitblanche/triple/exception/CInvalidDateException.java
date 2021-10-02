package com.nuitblanche.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CInvalidDateException extends RuntimeException{

    public CInvalidDateException(String message){
        super(message);
    }

    public CInvalidDateException(String message, Throwable cause){
        super(message, cause);
    }
}
