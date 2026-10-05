package com.eventify.eventify.controller.web;

import com.eventify.eventify.exception.InvalidEnterException;
import com.eventify.eventify.exception.ResourceNotFoundException;
import com.eventify.eventify.entity.Event;
import com.eventify.eventify.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest; // Spring Boot 3.x: org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(EventWebController.class)
public class EventWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Test
    void list_WithEvents_ReturnsListViewWithEventsInModel() throws Exception{

        List<Event> events = List.of(
                new Event(1L, "Conferencia Java", LocalDate.of(2026,10,10), "Charla Tecnica"),
                new Event(2L, "Workshop Spring", LocalDate.of(2026,11,20), "Taller practico")
        );
        when(eventService.listarTodos()).thenReturn(events);

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events/list"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attribute("events", hasSize(2)))
                .andExpect(content().string(containsString("Conferencia Java")))
                .andExpect(content().string(containsString("Workshop Spring")));
    }

    @Test
    void list_WithEvents_ShowsFriendlyEmptyMessage() throws Exception{
        when(eventService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/admin/events"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events/list"))
                .andExpect(model().attribute("events", empty()))
                .andExpect(content().string(containsString("Actualmente no hay eventos programados")))
                .andExpect(content().string(not(containsString("<table"))));
    }

    // ---------- Formulario de creación ----------
    @Test
    void showCreateForm_ReturnsFormWithEmptyEvent() throws Exception {
        mockMvc.perform(get("/admin/events/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events/form"))
                .andExpect(model().attributeExists("event"));
    }

    // ---------- Escenario 3: registro y redirección ----------
    @Test
    void create_ValidEvent_RedirectsToListWithFlashMessage() throws Exception {
        // Arrange
        when(eventService.crearEvento(any(Event.class)))
                .thenReturn(new Event(1L, "Cumbre IA", LocalDate.of(2026,11,30), "Encuentro"));

        // Act & Assert
        mockMvc.perform(post("/admin/events")
                        .param("nombre", "Cumbre IA")
                        .param("fecha", "2026-11-30")
                        .param("descripcion", "Encuentro"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"))
                .andExpect(flash().attribute("mensaje", "Evento registrado correctamente"));

        verify(eventService, times(1)).crearEvento(any(Event.class));
    }

    @Test
    void create_EmptyName_ReturnsFormWithErrorAndDoesNotRedirect() throws Exception {
        // Arrange
        when(eventService.crearEvento(any(Event.class)))
                .thenThrow(new InvalidEnterException("El nombre del evento no puede estar vacío"));

        // Act & Assert
        mockMvc.perform(post("/admin/events")
                        .param("nombre", "")
                        .param("fecha", "2026-11-30"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events/form"))
                .andExpect(model().attribute("error", "El nombre del evento no puede estar vacío"))
                .andExpect(model().attribute("event", hasProperty("fecha", is(LocalDate.of(2026,11,30)))));
    }

    // ---------- Edición ----------
    @Test
    void showEditForm_ExistingId_ReturnsFormWithEvent() throws Exception {
        // Arrange
        when(eventService.obtenerEventById(1L)).thenReturn(new Event(1L, "Conferencia Java", LocalDate.of(2026,10,10), "Desc"));

        // Act & Assert
        mockMvc.perform(get("/admin/events/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/events/form"))
                .andExpect(model().attribute("event", hasProperty("nombre", is("Conferencia Java"))));
    }

    @Test
    void showEditForm_NonExistingId_ReturnsErrorView404() throws Exception {
        // Arrange
        when(eventService.obtenerEventById(99L))
                .thenThrow(new ResourceNotFoundException("Evento no encontrado con id: 99"));

        // Act & Assert
        mockMvc.perform(get("/admin/events/99/edit"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("admin/error"))
                .andExpect(model().attribute("errorMessage", "Evento no encontrado con id: 99"));
    }

    @Test
    void update_ValidEvent_RedirectsToList() throws Exception {
        // Arrange
        when(eventService.actualizarEvent(eq(1L), any(Event.class)))
                .thenReturn(new Event(1L, "Nombre nuevo", LocalDate.of(2026,10,10), "Desc"));

        // Act & Assert
        mockMvc.perform(post("/admin/events/1")
                        .param("nombre", "Nombre nuevo")
                        .param("fecha", "2026-10-10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"))
                .andExpect(flash().attribute("mensaje", "Evento actualizado correctamente"));
    }

    // ---------- Eliminación ----------
    @Test
    void delete_ExistingId_RedirectsToListWithFlashMessage() throws Exception {
        mockMvc.perform(post("/admin/events/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/events"))
                .andExpect(flash().attribute("mensaje", "Evento eliminado correctamente"));

        verify(eventService).eliminarEvent(1L);
    }

    @Test
    void delete_NonExistingId_ReturnsErrorView404() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Evento no encontrado con id: 99"))
                .when(eventService).eliminarEvent(99L);

        // Act & Assert
        mockMvc.perform(post("/admin/events/99/delete"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("admin/error"));
    }








}
