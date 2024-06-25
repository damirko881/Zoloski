package com.zoo.controllers;

import com.zoo.models.*;
import com.zoo.service.IncidentService;
import com.zoo.service.NastambaService;
import com.zoo.service.TrosakService;
import com.zoo.service.ZivotinjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class IncidentController {

    private final IncidentService incidentService;
    private final NastambaService nastambaService;
    private final TrosakService trosakService;
    private final ZivotinjaService zivotinjaService;

    @Autowired
    public IncidentController(IncidentService incidentService, NastambaService nastambaService, TrosakService trosakService, ZivotinjaService zivotinjaService) {
        this.incidentService = incidentService;
        this.nastambaService = nastambaService;
        this.trosakService = trosakService;
        this.zivotinjaService = zivotinjaService;
    }

    @GetMapping("/dodaj-incident")
    public String showCreateForm(Model model) {
        List<Nastamba> nastambe = nastambaService.findAll();
        List<Zivotinja> zivotinje = zivotinjaService.findAll();
        model.addAttribute("incident", new Incident());
        model.addAttribute("nastambe", nastambe);
        model.addAttribute("zivotinje", zivotinje);
        return "/Incident/dodaj-incident";
    }

    @PostMapping("/dodaj-incident")
    public String createIncident(@Valid @ModelAttribute("incident") Incident incident, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Nastamba> nastambe = nastambaService.findAll();
            model.addAttribute("nastambe", nastambe);
            return "/Incident/dodaj-incident";
        }
        incidentService.save(incident);

        // Dodaj trošak popravka
        Trosak trosak = new Trosak(LocalDate.now(), TipTroska.POPRAVCI, new BigDecimal(incident.getTroskovi()), incident.getZivotinja());
        trosakService.save(trosak);

        return "redirect:/dashboard";
    }

    @GetMapping("/prikazi-incidente")
    public String prikaziIncidente(Model model) {
        List<Incident> incidenti = incidentService.findAll();
        model.addAttribute("incidenti", incidenti);
        return "/Incident/prikazi-incidente";
    }
    @GetMapping("/obrisi-incident/{id}")
    public String deleteIncident(@PathVariable("id") Long id, Model model) {
        incidentService.deleteById(id);
        return "redirect:/prikazi-incidente";
    }
    @GetMapping("/uredi-incident/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Incident incident = incidentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nevažeći ID incidenta: " + id));
        List<Nastamba> nastambe = nastambaService.findAll();
        List<Zivotinja> zivotinje = zivotinjaService.findAll();
        model.addAttribute("incident", incident);
        model.addAttribute("nastambe", nastambe);
        model.addAttribute("zivotinje", zivotinje);
        return "/Incident/uredi-incident";
    }

    @PostMapping("/uredi-incident/{id}")
    public String updateIncident(@PathVariable("id") Long id, @Valid @ModelAttribute("incident") Incident incident, BindingResult result, Model model) {
        if (result.hasErrors()) {
            incident.setId(id);
            List<Nastamba> nastambe = nastambaService.findAll();
            List<Zivotinja> zivotinje = zivotinjaService.findAll();
            model.addAttribute("nastambe", nastambe);
            model.addAttribute("zivotinje", zivotinje);
            return "/Incident/uredi-incident";
        }

        incidentService.save(incident);
        return "redirect:/prikazi-incidente";
    }

}