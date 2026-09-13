package com.eventify.eventify.config;

import com.eventify.eventify.model.Event;
import com.eventify.eventify.model.Venue;
import com.eventify.eventify.service.EventService;
import com.eventify.eventify.service.VenueService;
import org.springframework.boot.CommandLineRunner; // interfaz de Spring Boot que permite ejecutar código automáticamente cuando la aplicación termina de iniciar.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataSeederConfig {

    @Bean
    public CommandLineRunner cargarDatos(EventService evento, VenueService lugar){
        return args -> { // args representa los argumentos que Spring Boot le entrega al CommandLineRunner.
            // Y esos argumentos son: String[] args
            evento.crearEvento(new Event(
                        null,
                        "Hackaton",
                        LocalDate.of(2026, 12, 9),
                        ", Actividad recreativa con Coders"));
            evento.crearEvento(new Event(null,
                        "Conferencia Tech 2026",
                        LocalDate.of(2026, 11, 17),
                        "Encuentro anual de desarrollo"));

            lugar.crearVenue(new Venue(
                        null,
                        "Riwi",
                        "Cra 170# 100-60",
                        100));
            lugar.crearVenue(new Venue(null,
                        "Auditorio Tecnológico",
                        "Calle Innovación 456",
                        150));

            System.out.println("Los datos se estan iniciando...");
        }; // el } cierra la  lambda y ; termina la instruccion return
    }
}