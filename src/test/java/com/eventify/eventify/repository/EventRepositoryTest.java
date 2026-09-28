package com.eventify.eventify.repository;
import com.eventify.eventify.entity.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("Debe guardar un Event correctamente y asignar su ID de forma automatica")
    void gardarEventTest(){
        Event event = new Event();
        event.setNombre("Hackathon Barranquilla");
        event.setFecha(LocalDate.of(2026, 11, 8));
        event.setDescripcion("El formato clásico de competencia de programación.");

        Event eventGuardado = eventRepository.save(event);

        assertThat(eventGuardado).isNotNull();
        assertThat(eventGuardado.getId()).isNotNull();
        assertThat(eventGuardado.getNombre()).isEqualTo("Hackathon Barranquilla");

    }

    @Test
    @DisplayName("Debe filtrar Event por nombre que coincidan entre si usando la Derived Query paginada")
    void findByNombreContainingTest(){
        Event event1 = new Event();
        event1.setNombre("Laboratorio de Codigo Abierto");
        event1.setFecha(LocalDate.of(2026, 10, 17));
        event1.setDescripcion("Taller práctico sobre en proyectos globales de software libre.");
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setNombre("Cumbre del Codigo Moderno");
        event2.setFecha(LocalDate.of(2026, 11, 5));
        event2.setDescripcion("Conferencia sobre mejores prácticas de ingeniería de software.");
        eventRepository.save(event2);

        Event event3 = new Event();
        event3.setNombre("Simposio de Inteligencia Artificial Aplicada");
        event3.setFecha(LocalDate.of(2026, 12, 2));
        event3.setDescripcion("Espacio académico y empresarial  sobre flujos de trabajo actuales");
        eventRepository.save(event3);

        PageRequest pageable = PageRequest.of(0,10);

        Page<Event> resultado = eventRepository.findByNombreContaining("Codigo", pageable);
        assertThat(resultado.getTotalElements()).isEqualTo(2);
        assertThat(resultado.getContent())
                .extracting(Event::getNombre)
                .containsExactlyInAnyOrder("Laboratorio de Codigo Abierto", "Cumbre del Codigo Moderno");
    }
}