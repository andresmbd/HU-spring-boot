
CREATE TABLE venue (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- GenerationType.IDENTITY, en PostgreSQL 10+ se traduce utilizando la sintaxis estándar GENERATED ALWAYS AS IDENTITY (o tipos BIGSERIAL
    nombre_lugar VARCHAR(100) NOT NULL,
    direccion_lugar VARCHAR(100) NOT NULL,
    capacidad_maxima INT NOT NULL
);


CREATE TABLE event (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_evento VARCHAR(100) NOT NULL,
    fecha_evento DATE NOT NULL,
    descripcion_evento VARCHAR(255) NOT NULL
);