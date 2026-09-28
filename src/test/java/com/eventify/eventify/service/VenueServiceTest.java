package com.eventify.eventify.service;

import com.eventify.eventify.entity.Event;
import com.eventify.eventify.exception.InvalidEnterException;
import com.eventify.eventify.entity.Venue;
import com.eventify.eventify.repository.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
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
        when(venueRepository.save(venueValido)).thenReturn(mockVenue);

        Venue resultado = venueService.crearVenue(venueValido);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Auditorio Principal", resultado.getNombre());
        verify(venueRepository, times(1)).save(venueValido);

    }

    @Test
    void registrarNombreVacio_ThrowsValidationException(){
        Venue venueInvalido = new Venue(null, "", "Calle 50 #20-10", 130);

        assertThrows(InvalidEnterException.class, ()-> venueService.crearVenue(venueInvalido));
        verify(venueRepository, never()).save(any());
    }

    @Test
    void findAllVenues_ReturnsFullList(){
        List<Venue> venues = List.of(
                new Venue(1L, "Lugar 1", "Cra 39# 49-660", 300),
                new Venue(2L, "Lugar 2", "Ave 10# 110-50", 250)
        );
        when(venueRepository.findAll(any(org.springframework.data.domain.Sort.class))) // quiere decir: pasamelo como sea que este ordenado
                .thenReturn(venues);
        List<Venue> resultado = venueService.listarTodos();
        assertEquals(2, resultado.size());

    }

}