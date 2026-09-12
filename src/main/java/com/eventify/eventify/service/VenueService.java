package com.eventify.eventify.service;

import com.eventify.eventify.model.Venue;
import com.eventify.eventify.repository.EventRepository;
import com.eventify.eventify.repository.VenueRepository;
import com.eventify.eventify.exeption.ValidationExeption;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    private final VenueRepository venueRepo;

    public VenueService(VenueRepository venueRepo){
        this.venueRepo = venueRepo;
    }

    public Venue crearVenue(Venue venue){
        if (venue == null) throw new ValidationExeption("El lugar no puede ser nulo");

        if(venue.getNombre() == null || venue.getNombre().trim().isBlank())
            throw new ValidationExeption("El nombre del lugar no puede estar vacio");

        if(venue.getDireccion() == null || venue.getDireccion().trim().isBlank())
            throw new ValidationExeption("La direccion del lugar necesita ser agregada");

        return venueRepo.guardar(venue);
    }


}
