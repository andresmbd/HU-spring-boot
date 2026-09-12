package com.eventify.eventify.repository;

import com.eventify.eventify.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VenueRepository {
    private Map<Long, Venue> lugares = new HashMap<>();
    private Long idCounter = 1L;

    public Venue guardar(Venue venue){
        if(venue.getId() == null){
            venue.setId(idCounter);
            idCounter++;
        }
        lugares.put(venue.getId(), venue);
        return venue;
    }

    public Map<Long, Venue> listarTodos(){
        return lugares;
    }

}
