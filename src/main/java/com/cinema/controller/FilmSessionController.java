package com.cinema.controller;

import com.cinema.service.FilmSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@Controller
public class FilmSessionController {

    private final FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/schedule")
    public String schedule(Model model) {
        try {
            model.addAttribute("filmSession", filmSessionService.findAll());
            return "sessions/schedule";
        } catch (NoSuchElementException e) {
            model.addAttribute("error", "Can't find any sessions");
            return "error/404";
        }
    }

    @GetMapping("/filmSession/{id}")
    public String filmSession(Model model, @PathVariable int id) {
        try {
            model.addAttribute("filmSession", filmSessionService.findById(id));
            return "filmSession";
        } catch (NoSuchElementException e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}
