package com.eventify.eventify.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Cuando es causada una exeption, Spring busca la respuesta asociada
 * a la excepción lanzada. Como esta clase InvalidDataException está anotada con
 * @ResponseStatus(HttpStatus.BAD_REQUEST), Spring
 * automáticamente detiene el proceso y devuelve un código de
 * error 400 Bad Request al cliente.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request
public class ValidationExeption extends RuntimeException{
    public ValidationExeption(String message){
        super(message);
    }
}
