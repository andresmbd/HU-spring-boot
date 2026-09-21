package com.eventify.eventify.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data // genera Getters, Setters, toString(), equals(), hashCode(); imcluso Un constructor requerido por los campos final y @NonNull.
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venue")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_lugar",nullable = false, length = 100)
    private String nombre;

    @Column(name = "direccion_lugar", nullable = false, length = 100)
    private String direccion;

    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidad;

}
