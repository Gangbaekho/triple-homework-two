package com.nuitblanche.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CDuplicatedTravelException extends RuntimeException {

    public CDuplicatedTravelException(String message){
        super(message);
    }

    public CDuplicatedTravelException(String message, Throwable cause){
        super(message, cause);
    }
}
