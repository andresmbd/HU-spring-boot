package com.eventify.eventify.service;

import com.eventify.eventify.entity.Venue;
import com.eventify.eventify.exeption.ResourceNotFoundException;
import com.eventify.eventify.repository.VenueRepository;
import com.eventify.eventify.exeption.InvalidEnterException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {
    private final VenueRepository venueRepo;

    public VenueService(VenueRepository venueRepo){
        this.venueRepo = venueRepo;
    }

    private Venue validarVenue(Venue venue){
        if (venue == null) throw new InvalidEnterException("El lugar no puede ser nulo");

        if(venue.getNombre() == null || venue.getNombre().trim().isBlank())
            throw new InvalidEnterException("El nombre del lugar no puede estar vacio");

        if(venue.getDireccion() == null || venue.getDireccion().trim().isBlank())
            throw new InvalidEnterException("La direccion del lugar necesita ser agregada");

        if (venue.getCapacidad() == null || venue.getCapacidad() <= 0)
            throw new InvalidEnterException("La capacidad no puede ser vacia ni ser menor a que 1");

        return venue;
    }

    public Venue crearVenue(Venue venue){
        Venue venueValidado = validarVenue(venue);
        return venueRepo.save(venueValidado);
    }

    public Venue obtenerVenueById(Long id){
        return venueRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se encontro el id: "+id+" de la entidad Venue"));
    }

    public Page<Venue> buscarPorNombre(String nombreClave, Pageable pageable){
        if (nombreClave != null || !nombreClave.isBlank())
            return venueRepo.findByNombreContaining(nombreClave, pageable);
        return venueRepo.findAll(pageable);
    }

    public Venue actualizarVenue(Long id, Venue venue){
        Venue venueExistente = venueRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No se encontro el id: "+id+" de la entidad Venue"));
        Venue venueValidado = validarVenue(venue);

        venueExistente.setNombre(venueValidado.getNombre());
        venueExistente.setDireccion(venueValidado.getDireccion());
        venueExistente.setCapacidad(venueValidado.getCapacidad());

        return venueExistente;
    }

    public void eliminarVenue(Long id){
        if(!venueRepo.existsById(id)) throw new ResourceNotFoundException("No se encontro el id: "+id+" de la entidad Venue");

        venueRepo.deleteById(id);
    }

}
