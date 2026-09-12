package com.eventify.eventify.model;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data // genera Getters, Setters, toString(), equals(), hashCode(); imcluso Un constructor requerido por los campos final y @NonNull.
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    private Long id;
    private String nombre;
    private String direccion;
    private Integer capacidad;

}
