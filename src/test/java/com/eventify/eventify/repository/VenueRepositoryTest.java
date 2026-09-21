package com.eventify.eventify.repository;

import com.eventify.eventify.entity.Venue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Levanta automáticamente la base de datos de pruebas
 * en memoria (H2) y crea la tabla venue basándose en
 * tu clase @Entity.
 */
@DataJpaTest // Le indica a Spring que prepare únicamente el entorno de persistencia (JPA/Hibernate).
public class VenueRepositoryTest {
    @Autowired // Inyecta la implementación real de tu repositorio
    private VenueRepository venueRepository; // queremos probar consultas SQL reales ejecutándose contra la base de datos en memoria.

    @Test
    @DisplayName("Debe guardar un Venue correctamente y asignar su ID de forma automatica") // asignarle un nombre descriptivo y personalizado a una clase o metodo de prueba.
    void guardarVenueTest(){
        Venue venue = new Venue();

        venue.setNombre("Estadio Romelio Martinez");
        venue.setDireccion("Cra8 # 18-189");
        venue.setCapacidad(14000);

        Venue venueGuardado = venueRepository.save(venue);

        // ASSERT (Verificar que la BD procesó el guardado)
        assertThat(venueGuardado).isNotNull();
        assertThat(venueGuardado.getId()).isNotNull();
        assertThat(venueGuardado.getNombre()).isEqualTo("Estadio Romelio Martinez");

    }
    @Test
    @DisplayName("Debe filtrar Venues por nombre que coincidan entre si usando la Derived Query paginada")
    void findByNombreContainingTest(){
        Venue venue1 = new Venue();
        venue1.setNombre("Estadio El Campín");
        venue1.setDireccion("Av. NQS");
        venue1.setCapacidad(39000);
        venueRepository.save(venue1);

        Venue venue2 = new Venue();
        venue2.setNombre("Estadio Atanasio Girardot");
        venue2.setDireccion("Cra 74");
        venue2.setCapacidad(45000);
        venueRepository.save(venue2);

        Venue venue3 = new Venue();
        venue3.setNombre("Centro de Eventos La Macarena");
        venue3.setDireccion("Autopista Norte");
        venue3.setCapacidad(10000);
        venueRepository.save(venue3);

        PageRequest pageable = PageRequest.of(0, 10);

        Page<Venue> resultado = venueRepository.findByNombreContaining("Estadio", pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(2);
        assertThat(resultado.getContent()).extracting(Venue::getNombre).containsExactlyInAnyOrder("Estadio El Campín", "Estadio Atanasio Girardot");
    }

}
