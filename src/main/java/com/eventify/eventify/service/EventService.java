package com.eventify.eventify.service;

import com.eventify.eventify.exeption.ValidationExeption;
import com.eventify.eventify.model.Event;
import com.eventify.eventify.repository.EventRepository;

import java.util.Map;

public class EventService {
    private final EventRepository eventRepo;


     public  EventService(EventRepository eventRepo){
         this.eventRepo=eventRepo;
     }

     public Event crearEvento(Event evento){
         if(evento == null){
             throw new ValidationExeption("El evento no puede estar en nulo");
         }
         if(evento.getNombre() == null || evento.getNombre().trim().isBlank()){
             throw new ValidationExeption("El nombre del evento no puedes estar vacio");
         }
         if(evento.getFecha() == null){
             throw new ValidationExeption("La fecha no puede quedar vacia");
         }
         return eventRepo.guardar(evento);
     }

     public Map<Long, Event> listarTodos(){
         return eventRepo.listarTodos();
     }
}
