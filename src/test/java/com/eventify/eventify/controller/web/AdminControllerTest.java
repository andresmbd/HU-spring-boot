package com.eventify.eventify.controller.web;

import com.eventify.eventify.entity.Event;
import com.eventify.eventify.entity.Venue;
import com.eventify.eventify.service.EventService;
import com.eventify.eventify.service.VenueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest; // Spring Boot 3.x: org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private VenueService venueService;

    // Escenario 4: el Model contiene la lista de eventos (y de lugares) que necesita la vista
    @Test
    void dashboard_ReturnsDashboardViewWithEventsAndVenuesInModel() throws Exception {
        // Arrange
        when(eventService.listarTodos()).thenReturn(List.of(
                new Event(1L, "Conferencia Tech 2026", LocalDate.of(2026,10,15), "Encuentro anual")));
        when(venueService.listarTodos()).thenReturn(List.of(
                new Venue(1L, "Centro de Convenciones", "Av. El Sol 123", 500),
                new Venue(2L, "Auditorio Tecnológico", "Calle Innovación 456", 150)));

        // Act & Assert
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attribute("events", hasSize(1)))
                .andExpect(model().attribute("venues", hasSize(2)))
                .andExpect(model().attribute("totalEventos", 1))
                .andExpect(model().attribute("totalLugares", 2));
    }

    @Test
    void home_RedirectsToAdmin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

}
