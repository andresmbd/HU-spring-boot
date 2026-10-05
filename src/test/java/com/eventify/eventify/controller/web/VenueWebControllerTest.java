package com.eventify.eventify.controller.web;

import com.eventify.eventify.exception.InvalidEnterException;
import com.eventify.eventify.entity.Venue;
import com.eventify.eventify.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest; // Spring Boot 3.x: org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VenueWebController.class)
class VenueWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VenueService venueService;

    @Test
    void list_WithVenues_ReturnsListViewWithVenuesInModel() throws Exception {
        when(venueService.listarTodos()).thenReturn(List.of(
                new Venue(1L, "Auditorio Principal", "Calle 50 #20-10", 300)));

        mockMvc.perform(get("/admin/venues"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/venues/list"))
                .andExpect(model().attribute("venues", hasSize(1)))
                .andExpect(content().string(containsString("Auditorio Principal")));
    }

    @Test
    void list_WithoutVenues_ShowsFriendlyEmptyMessage() throws Exception {
        when(venueService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/admin/venues"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Actualmente no hay lugares registrados")));
    }

    @Test
    void create_ValidVenue_RedirectsToList() throws Exception {
        when(venueService.crearVenue(any(Venue.class)))
                .thenReturn(new Venue(1L, "Sala Norte", "Carrera 10 #20-30", 80));

        mockMvc.perform(post("/admin/venues")
                        .param("nombre", "Sala Norte")
                        .param("direccion", "Carrera 10 #20-30")
                        .param("capacidad", "80"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/venues"))
                .andExpect(flash().attribute("mensaje", "Lugar registrado correctamente"));
    }

    @Test
    void create_EmptyName_ReturnsFormWithError() throws Exception {
        when(venueService.crearVenue(any(Venue.class)))
                .thenThrow(new InvalidEnterException("El nombre del lugar no puede estar vacío"));

        mockMvc.perform(post("/admin/venues")
                        .param("nombre", "")
                        .param("direccion", "Carrera 10 #20-30")
                        .param("capacidad", "80"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/venues/form"))
                .andExpect(model().attributeExists("error"));
    }
}