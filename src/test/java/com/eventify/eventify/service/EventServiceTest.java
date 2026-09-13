package com.eventify.eventify.service;

import com.eventify.eventify.exeption.ValidationExeption;
import com.eventify.eventify.model.Event;
import com.eventify.eventify.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock // Crea un objeto falso o simulador de una clase.
    private EventRepository eventRepository; // Se Crea un EventRepository simulado que responde exactamente lo que se le indique durante la prueba.

    @InjectMocks //  Crea la instancia real que se quiere probar e introduce en ella los simuladores (@Mock).
    private EventService eventService; // entrega un EventService real que utilizará el repositorio falso.

    private Event eventoValido;

    @BeforeEach // ejecutar un metodo antes de cada prueba (@Test). prepara el estado inicial que cada prueba necesita.
    void setUp(){ // setUp() se ejecuta antes de cada @Test.
        eventoValido = new Event(null, "Conferencia java", LocalDate.of(2026, 10, 10), "Charla técnica de backend");
    }

    @Test
    void registrarEventoValido(){
        Event mockEvent = new Event(1L,"Conferencia Java", LocalDate.of(2026,10,10), "Charla técnica de backend");
        when(eventRepository.guardar(eventoValido)).thenReturn(mockEvent);

        Event resultado = eventService.crearEvento(eventoValido);

        assertNotNull(resultado); // Comprueba que este valor NO sea null
        assertEquals(1L, resultado.getId()); // Comprueba que dos valores sean iguales.
        assertEquals("Conferencia Java", resultado.getNombre());
        verify(eventRepository, times(1)).guardar(eventoValido); // comprueba si el metodo fue llamado O sea eventRepository.guardar(eventoValido) haya sido ejecutado en este caso 1 vez

    }

    @Test
    void registrarNombreVacio_ThrowsValidationExeptionn(){
        Event eventoInvalido = new Event(null, "  ", LocalDate.of(2026, 10,10), "Descripcion");

        // comprobar que un código lanza una excepción
        assertThrows(ValidationExeption.class,
                () -> eventService.crearEvento(eventoInvalido)); // Se Espera que al ejecutar este código se produzca una ValidationExeption
        verify(eventRepository, never()).guardar(any());
    }

    void listarTodos(){
        Map<Long, Event> mockMap = new HashMap<>();
        mockMap.put(1L, new Event(1L, "Evento 1", LocalDate.of(2026,10,10), "Descripcion"));
        when(eventRepository.listarTodos()).thenReturn(mockMap);

        Map<Long, Event> resultado = eventService.listarTodos();

        assertNull(resultado);
        assertEquals(1,resultado.size());
        verify(eventRepository, times(1)).listarTodos();
    }



}
