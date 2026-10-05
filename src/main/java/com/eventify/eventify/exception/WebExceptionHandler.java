package com.eventify.eventify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *  Es la versión "para páginas" de @RestControllerAdvice.
 *  Escucha excepciones de los controladores y decide qué vista mostrar.
 */
@ControllerAdvice(basePackages = "com.eventify.eventify.controller.web")
public class WebExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException e){
        ModelAndView mav = new ModelAndView("admin/error");
        mav.addObject("errorMessage", e.getMessage());
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
