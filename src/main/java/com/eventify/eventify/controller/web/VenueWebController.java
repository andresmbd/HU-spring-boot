package com.eventify.eventify.controller.web;

import com.eventify.eventify.entity.Venue;
import com.eventify.eventify.exception.InvalidEnterException;
import com.eventify.eventify.service.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/venues")
public class VenueWebController {
    private static final String VISTA_LISTA = "admin/venues/list";
    private static final String VISTA_FORM = "admin/venues/form";
    private static final String REDIRECT_LISTA = "redirect:/admin/venues";

    private final VenueService venueService;

    public VenueWebController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("venues", venueService.listarTodos());
        return VISTA_LISTA;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("venue", new Venue());
        return VISTA_FORM;
    }

    @PostMapping
    public String create(@ModelAttribute("venue") Venue venue,
                         Model model,
                         RedirectAttributes redirectAttributes){
        venue.setId(null);
        try{
            venueService.crearVenue(venue);
        } catch (InvalidEnterException e) {
            model.addAttribute("error", e.getMessage());
            return VISTA_FORM;
        }
        redirectAttributes.addFlashAttribute("mensaje", "Lugar registrado correctamente");
        return REDIRECT_LISTA;
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("venue", venueService.obtenerVenueById(id));
        return VISTA_FORM;
    }


    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("venue") Venue venue,
                         Model model,
                         RedirectAttributes redirectAttributes){
        try {
            venueService.actualizarVenue(id, venue);
        } catch (InvalidEnterException e) {
            venue.setId(id);
            model.addAttribute("error", e.getMessage());
            return VISTA_FORM;
        }
        redirectAttributes.addFlashAttribute("venue", "Lugar actualizado correctamente");
        return REDIRECT_LISTA;
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        venueService.eliminarVenue(id);
        redirectAttributes.addFlashAttribute("mensaje", "Lugar eliminado correctamente");
        return REDIRECT_LISTA;
    }


}
