package com.eventify.eventify.config;

import com.eventify.eventify.model.Event;
import com.eventify.eventify.model.Venue;
import com.eventify.eventify.repository.EventRepository;
import com.eventify.eventify.repository.VenueRepository;
import org.springframework.boot.CommandLineRunner; // interfaz de Spring Boot que permite ejecutar código automáticamente cuando la aplicación termina de iniciar.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataSeederConfig {

    @Bean
    public CommandLineRunner cargarDatos(EventRepository evento, VenueRepository lugar){
        return args -> { // args representa los argumentos que Spring Boot le entrega al CommandLineRunner.
            // Y esos argumentos son: String[] args
            evento.guardar(new Event(
                    1L,
                    "Hackaton",
                    LocalDate.now(),
                    ", Actividad recreativa con Coders"));

            lugar.guardar(new Venue(
                    1L,
                    "Riwi",
                    "Cra 170# 100-60",
                    100));

            System.out.println("Los datos se estan iniciando...");
        }; // el } cierra la  lambda y ; termina la instruccion return
    }
}