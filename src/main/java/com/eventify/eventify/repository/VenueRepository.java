package com.eventify.eventify.repository;

import com.eventify.eventify.entity.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    Page<Venue> findByNombreContaining(String nombreClave, Pageable pageable);
}
