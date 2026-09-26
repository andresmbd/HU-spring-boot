package com.eventify.eventify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * No hay que llamar los metodos en ningún lado.
 * Spring Boot los llama de forma automática tras bambalinas.
 *
 * Gracias a la anotación @RestControllerAdvice y a @ExceptionHandler,
 * Spring Boot se mantiene "escuchando" todo tu proyecto.
 */
@RestControllerAdvice(basePackages = "com.eventify.eventify.controller.api")
public class GlobalExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerResourceNotFoundException(ResourceNotFoundException e){
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InvalidEnterException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidEnterException(InvalidEnterException e){
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus httpStatus, String message){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", httpStatus.value());
        body.put("error", httpStatus.getReasonPhrase());
        body.put("message", message);
        // el httpStatus se envía en las cabeceras HTTP (el HTTP Status Code)
        return ResponseEntity.status(httpStatus)
                             .body(body);
    }



}
