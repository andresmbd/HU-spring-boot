package com.eventify.eventify.service;

import com.eventify.eventify.entity.Event;
import com.eventify.eventify.exception.InvalidEnterException;
import com.eventify.eventify.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock // Crea un objeto falso o simulador de una clase.
    private EventRepository eventRepository; // Se Crea un EventRepository simulado que responde exactamente lo que se le indique durante la prueba.

    @InjectMocks //  Crea la instancia real que se quiere probar e introduce en ella los simuladores (@Mock).
    private EventService eventService; // entrega un EventService real que utilizará el repositorio falso.

    private Event eventoValido;

    @BeforeEach // Ejecutar un metodo antes de cada prueba (@Test). Prepara el estado inicial que cada prueba necesita.
    void setUp(){ // setUp() se ejecuta antes de cada @Test.
        eventoValido = new Event(null, "Conferencia java", LocalDate.of(2026, 10, 10), "Charla técnica de backend");
    }

    @Test
    void registrarEventoValido(){
        Event mockEvent = new Event(1L,"Conferencia Java", LocalDate.of(2026,10,10), "Charla técnica de backend");
        when(eventRepository.save(eventoValido)).thenReturn(mockEvent);

        Event resultado = eventService.crearEvento(eventoValido);

        assertNotNull(resultado); // Comprueba que este valor NO sea null
        assertEquals(1L, resultado.getId()); // Comprueba que dos valores sean iguales.
        assertEquals("Conferencia Java", resultado.getNombre());
        verify(eventRepository, times(1)).save(eventoValido); // comprueba si el metodo fue llamado, O sea eventRepository.guardar(eventoValido) haya sido ejecutado en este caso 1 vez

    }

    @Test
    void registrarNombreVacio_ThrowsValidationExeptionn(){
        Event eventoInvalido = new Event(null, "  ", LocalDate.of(2026, 10,10), "Descripcion");

        // comprobar que un código lanza una excepción
        assertThrows(InvalidEnterException.class,
                () -> eventService.crearEvento(eventoInvalido)); // Se espera que al ejecutar este código se produzca una ValidationExeption
        verify(eventRepository, never()).save(any());
    }

    @Test
    void findAll_ReturnsFullList(){

        List<Event> events = List.of(
                new Event(1L, "Evento 1", LocalDate.of(2026, 11, 12), "descripcion..."),
                new Event(2L, "Evento 2", LocalDate.of(2026, 10, 17), "descripcion...")
        );
        when(eventRepository.findAll(any(org.springframework.data.domain.Sort.class))) // quiere decir: pasamelo como sea que este ordenado
                .thenReturn(events);

        List<Event> resultado = eventService.listarTodos();

        assertEquals(2, resultado.size());

    }


}