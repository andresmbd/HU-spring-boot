package com.eventify.eventify.controller.web;

import com.eventify.eventify.entity.Event;
import com.eventify.eventify.entity.Venue;
import com.eventify.eventify.service.EventService;
import com.eventify.eventify.service.VenueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final EventService eventService;
    private final VenueService venueService;

    public AdminController(EventService eventService, VenueService venueService) {
        this.eventService = eventService;
        this.venueService = venueService;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String dashboard(Model model){
        List<Event> events = eventService.listarTodos();
        List<Venue> venues = venueService.listarTodos();

        model.addAttribute("events", events);
        model.addAttribute("venues", venues);
        model.addAttribute("totalEventos", events.size());
        model.addAttribute("totalLugares", venues.size());

        return "admin/dashboard";
    }

}
