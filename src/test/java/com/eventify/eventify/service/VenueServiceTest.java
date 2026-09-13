package com.eventify.eventify.service;

import com.eventify.eventify.exeption.ValidationExeption;
import com.eventify.eventify.model.Venue;
import com.eventify.eventify.repository.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VenueServiceTest {
    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private VenueService venueService;

    private Venue venueValido;

    @BeforeEach
    void setUp(){
        venueValido = new Venue(null, "Auditorio Principal", "Calle 50 #20-10", 130);
    }

    @Test
    void registrarVenueValido(){
        Venue mockVenue = new Venue(1L, "Auditorio Principal", "Calle 50 #20-10", 130);
        when(venueRepository.guardar(venueValido)).thenReturn(mockVenue);

        Venue resultado = venueService.crearVenue(venueValido);

        assertNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Auditorio Principal", resultado.getNombre());
        verify(venueRepository, times(1)).guardar(venueValido);

    }

    @Test
    void registrarNOmbreVacio_ThrowsValidationExeption(){
        Venue venueInvalido = new Venue(null, "", "Calle 50 #20-10", 130);

        assertThrows(ValidationExeption.class, ()-> venueService.crearVenue(venueInvalido));
        verify(venueRepository, never()).guardar(any());
    }

    void listarTodos(){
        Map<Long, Venue> mockMap = new HashMap<>();
        mockMap.put(1L, new Venue(1L, "Lugar A", "Direccion A", 100));
        when(venueRepository.listarTodos()).thenReturn(mockMap);

        Map<Long, Venue>resultado = venueService.listarTodos();

        assertNull(resultado);
        assertEquals(1, resultado.size());
        verify(venueRepository, times(1)).listarTodos();
    }

}
