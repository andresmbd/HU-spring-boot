package com.eventify.eventify.controller;

import com.eventify.eventify.entity.Event;
import com.eventify.eventify.entity.Venue;
import com.eventify.eventify.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/venue")
@Tag(name = "Lugares", description = "Operaciones de registro, consulta, actualizacion y eliminacion de lugares o sedes")
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

    @GetMapping("/{id}") // Spring retorna por si solo el Http.OK
    @Operation(summary = "Consulta un lugares registrado por su id", description = "Retorna el lugar ya almacenado")
    public Venue obtenerVenueById(@PathVariable("id") Long id){
        return venueService.obtenerVenueById(id);
    }

    @GetMapping
    @Operation(summary = "Buscar por nombre del lugar", description = "Se ejecuta la consulta si se pasa el @param String claveNombre si no, se pasa el paginado")
    /**
     * @RequestParam
     * Le indica a Spring que el parámetro viene en la URL
     * como una variable de consulta (query parameter),
     * por ejemplo: ?claveNombre=Estadio
     *
     * @ParameterObject
     * le indica a Swagger que descomponga el objeto
     * Pageable en campos individuales y amigables
     * en la interfaz gráfica: un cuadro de texto
     * para page, otro para size y otro para sort.
     */
    public ResponseEntity<Page<Venue>> listarPorNombre(
            @RequestParam(required = false) String claveNombre, // Le dice a Spring que este parámetro es opcional.
            @ParameterObject Pageable pageable) // le ordena a Swagger transformar ese parámetro en tres campos de formulario independientes en la interfaz web
    {
        Page<Venue> resultado = venueService.buscarPorNombre(claveNombre, pageable);
        return ResponseEntity.ok(resultado);
        /**
         * Al presionar el botón Execute en Swagger, la herramienta toma
         * los valores de esos tres campos de texto y construye
         * automáticamente la URL correcta:
         * GET /venues?page=0&size=10&sort=nombre,asc.
         */
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actulizar un lugar ya existente", description = "Actualiza un lugar por su id validando su existencia y con los datos originales")
    public Venue actualizarVenue(@PathVariable("id") Long id, Venue venue){
        return venueService.actualizarVenue(id, venue);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un lugar", description = "Elimina un lugar por su id validando su existencia")
    public void eliminarVenue(@PathVariable("id") Long id){
        venueService.eliminarVenue(id);
    }
}
