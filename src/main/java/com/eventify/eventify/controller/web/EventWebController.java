package com.eventify.eventify.controller.web;

import com.eventify.eventify.entity.Event;
import com.eventify.eventify.exception.InvalidEnterException;
import com.eventify.eventify.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/events")
public class EventWebController {

    private static final String VISTA_LISTA = "admin/events/list";
    private static final String VISTA_FORM = "admin/events/form";
    private static final String REDIRECT_LISTA = "redirect:/admin/events";

    private final EventService eventService;

    public EventWebController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("events", eventService.listarTodos());
        return VISTA_LISTA;
    }

    /**
     * Su propósito es mostrar el formulario vacío para crear un nuevo evento
     */
    @GetMapping("/new")
    public String showCreateForm(Model model){
        /**
         * Crea un objeto vacío de tipo Event y lo
         * envía a la vista para que los campos del
         * formulario HTML puedan vincularse a él (con th:object y th:field)
         */
        model.addAttribute("event", new Event());
        return VISTA_FORM;
    }

    @PostMapping
    public String create(@ModelAttribute("event") Event event, // Recibe los datos enviados desde el formulario HTML y los convierte automáticamente en un objeto Java de tipo Event.
                         Model model,
                         RedirectAttributes redirectAttributes){
        event.setId(null); // Un registro nuevo nunca debe traer id desde el formulario
        try {
            eventService.crearEvento(event);
        } catch (InvalidEnterException e) {
            model.addAttribute("error", e.getMessage());
            return VISTA_FORM;
        }
        // Si todo sale bien, guarda un mensaje de éxito temporalmente en la sesión.
        redirectAttributes.addFlashAttribute("mensaje", "Evento registrado correctamente");
        return REDIRECT_LISTA; // Redirige al usuario a la lista de eventos (/admin/events) aplicando el patrón Post-Redirect-Get.
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("event", eventService.obtenerEventById(id));
        return VISTA_FORM;
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                               @ModelAttribute("event") Event event,
                               Model model,
                               RedirectAttributes redirectAttributes){
        try {
            eventService.actualizarEvent(id, event);
        } catch (InvalidEnterException e) {
            event.setId(id); // Captura el id para que no se pierda en la transicion y se lo devuelve al input al dirigirse a la vista
            model.addAttribute("error", e.getMessage());
            return VISTA_FORM;
        }
        redirectAttributes.addFlashAttribute("mensaje", "Evento actualizado correctamente");
        return REDIRECT_LISTA;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        eventService.eliminarEvent(id);
        redirectAttributes.addFlashAttribute("mensaje", "Evento eliminado correctamente");
        return REDIRECT_LISTA;
    }



}
