package com.eventify.eventify.controller;

import com.eventify.eventify.model.Venue;
import com.eventify.eventify.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/venue")
@Tag(name = "Lugares", description = "Operaciones de registro y consulta de lugares o sedes")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService){
        this.venueService = venueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar un nuevo lugar", description = "Valida y almacena un lugar (venue) en memoria")
    public Venue registrarLugar(Venue venue){
        return venueService.crearVenue(venue);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Consultar los lugares registrados", description = "Retorna la coleccion de lugares ya almacenados")
    public Map<Long, Venue> listarTodos(){
        return venueService.listarTodos();
    }
}
