package com.eventify.eventify.service;

import com.eventify.eventify.exception.ResourceNotFoundException;
import com.eventify.eventify.exception.InvalidEnterException;
import com.eventify.eventify.entity.Event;
import com.eventify.eventify.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepo;


     public  EventService(EventRepository eventRepo){
         this.eventRepo=eventRepo;
     }

     private Event validarEvent(Event evento){
         if(evento == null){
             throw new InvalidEnterException("El evento no puede estar en nulo");
         }
         if(evento.getNombre() == null || evento.getNombre().trim().isBlank()){
             throw new InvalidEnterException("El nombre del evento no puedes estar vacio");
         }
         if(evento.getFecha() == null){
             throw new InvalidEnterException("La fecha no puede quedar vacia");
         }
         return evento;
     }

     public Event crearEvento(Event evento){
         Event eventoValidado = validarEvent(evento);
         return eventRepo.save(eventoValidado);
     }

     public Event obtenerEventById(Long id){
         return eventRepo.findById(id)
                 .orElseThrow(()-> new ResourceNotFoundException("No se encontro el id: "
                         +id+" de la entidad Event"));
     }

     public Page<Event> buscarEventosPorNombre(String nombreClave, Pageable pageable){
         if(nombreClave != null && !nombreClave.isBlank())
             // Si mandan un filtro por nombre, usamos la derived query
            return eventRepo.findByNombreContaining(nombreClave, pageable);

         // Si no mandan nada, listamos todo paginado
         return eventRepo.findAll(pageable);
     }

     public Event actualizarEvent(Long id, Event nuevoEvent){
         Event eventoExistente = eventRepo.findById(id)
                 .orElseThrow(()-> new ResourceNotFoundException("No se encontro el id: "
                 +id+" de la entidad Event"));

         Event eventoValidado = validarEvent(nuevoEvent);

         eventoExistente.setNombre(eventoValidado.getNombre());
         eventoExistente.setFecha(eventoExistente.getFecha());
         eventoExistente.setDescripcion(eventoExistente.getDescripcion());

         return eventoExistente;
     }

     public void eliminarEvent(Long id){
         if(!eventRepo.existsById(id)){
             throw new ResourceNotFoundException("No se encontro el id: "
                     +id+" de la entidad Event");
         }
         eventRepo.deleteById(id);
     }

     public List<Event> listarTodos(){
         List<Event> events = eventRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
         if (events.isEmpty()){
             throw new ResourceNotFoundException("La lista esta vacia");
         }
         return events;
     }
}
