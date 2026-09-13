package com.eventify.eventify.controller;

import com.eventify.eventify.model.Event;
import com.eventify.eventify.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/events")
/**
 * @Tag Sirve únicamente para organizar y categorizar la
 * documentación de la API en la interfaz de Swagger UI
 *
 * name: Es el título del grupo bajo el cual se agruparán
 * todos los endpoints de ese controlador.
 *
 * description: Es una breve explicación que describe qué
 * tipo de operaciones se manejan dentro de esta categoría
 */
@Tag(name = "Eventos", description = "Operaciones para registrar y consultar eventos")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201 Created
    /**
     * @Operation se coloca encima de cada método individual del
     * controlador para documentar qué hace ese endpoint específico
     * en Swagger UI.
     *
     * summary: Es un resumen o título corto de la acción que realiza
     * el método.
     *
     * description: Detalla con más precisión el comportamiento interno
     * o los requisitos de la operación
     */
    @Operation(summary = "Registrar un nuevo evento", description = "Valida y almacena un evento en memoria")
    /**
     * @RequestBody indica a Spring que tome el cuerpo de la petición
     * HTTP (el body que envía el cliente,
     * normalmente en formato JSON) y lo convierta
     * automáticamente en un objeto Java.
     */
    public Event registrarEvento(@RequestBody Event evento){ // Spring toma el JSON enviado y llena las propiedades del objeto 'evento'
        return eventService.crearEvento(evento);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200 ok
    @Operation(summary = "Consulatar los eventos registrados", description = "Retorna la lista todos los eventos almacenados en memoria")
    public Map<Long, Event> listarTodos(){
        return eventService.listarTodos();
    }
}
