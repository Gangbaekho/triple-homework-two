package com.nuitblanche.triple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlingConroller {

    @ExceptionHandler(CUserNotFoundException.class)
    public ResponseEntity<?> handleCUserNotFoundException (CUserNotFoundException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(CCityNotFoundException.class)
    public ResponseEntity<?> handleCCityNotFoundException (CCityNotFoundException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(CDuplicatedTravelException.class)
    public ResponseEntity<?> handleCDuplicatedTravelException (CDuplicatedTravelException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(CInvalidDateException.class)
    public ResponseEntity<?> handleCInvalidDateException (CInvalidDateException e){

        Map<String,String> message = new HashMap<>();
        message.put("message",e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
