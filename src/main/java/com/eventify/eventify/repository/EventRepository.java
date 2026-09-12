package com.eventify.eventify.repository;

import com.eventify.eventify.model.Event;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EventRepository {
    private Map<Long, Event> eventos = new HashMap<>();
    private Long idCounter = 1L;

    public Event guardar(Event event){
        if (event.getId() == null){
            event.setId(idCounter);
            idCounter++;
        }
        eventos.put(event.getId(), event);
        return event;
    }

    public Map<Long, Event> listarTodos(){
        return eventos;
    }
}
