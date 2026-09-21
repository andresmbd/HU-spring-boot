package com.eventify.eventify.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_evento", nullable = false, length = 100)
    private String nombre;

    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fecha;

    @Column(name = "descripcion_evento", nullable = false) // por defecto jpa e hibernate establecen por default length = 255
    private String descripcion;
}
