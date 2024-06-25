package com.zoo.controllers;

import com.zoo.models.*;
import com.zoo.repositories.RadnikRepository;
import com.zoo.service.OsposobljavanjeService;
import com.zoo.service.RadnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RadnikController {

    private final RadnikService radnikService;
    private final OsposobljavanjeService osposobljavanjeService;
    private final RadnikRepository radnikRepository;

    @Autowired
    public RadnikController(RadnikService radnikService, OsposobljavanjeService osposobljavanjeService, RadnikRepository radnikRepository) {
        this.radnikService = radnikService;
        this.osposobljavanjeService = osposobljavanjeService;
        this.radnikRepository = radnikRepository;
    }

    @GetMapping("/dodaj-radnika")
    public String prikaziFormuDodajRadnika(Model model) {
        List<Osposobljavanje> osposobljavanja = osposobljavanjeService.findAll();
        model.addAttribute("osposobljavanja", osposobljavanja);
        model.addAttribute("radnik", new Radnik());
        return "/Radnik/dodaj-radnika";
    }

    @PostMapping("/dodaj-radnika")
    public String dodajRadnika(@ModelAttribute Radnik radnik) {
        radnikService.save(radnik);
        return "redirect:/dashboard";
    }
    @GetMapping("/prikazi-sve-radnike")
    public String prikaziRadnike(Model model) {
        List<Radnik> radnici = radnikService.findAll();
        LocalDate danas = LocalDate.now();

        radnici.forEach(radnik -> {
            List<RadnoVrijeme> filtriranaRadnaVremena = radnik.getRadnaVremena().stream()
                    .filter(rv -> !rv.getDatum().isBefore(danas))
                    .collect(Collectors.toList());
            radnik.setRadnaVremena(filtriranaRadnaVremena);
        });

        model.addAttribute("radnici", radnici);
        return "/Radnik/prikazi-sve-radnike";
    }
    @GetMapping("/radnik/{id}")
    public String prikaziRadnika(@PathVariable Long id, Model model) {
        Optional<Radnik> radnik = radnikService.findById(id);
        if (radnik.isPresent()) {
            Radnik foundRadnik = radnik.get();
            LocalDate danas = LocalDate.now();
            List<RadnoVrijeme> filtriranaRadnaVremena = foundRadnik.getRadnaVremena().stream()
                    .filter(rv -> !rv.getDatum().isBefore(danas))
                    .collect(Collectors.toList());
            foundRadnik.setRadnaVremena(filtriranaRadnaVremena);
            model.addAttribute("radnik", foundRadnik);
            return "/Radnik/radnik-info";
        } else {
            return "error"; // ili neka druga odgovarajuća stranica u slučaju da radnik nije pronađen
        }
    }
    @GetMapping("/uredi-radnika/{id}")
    public String prikaziFormuZaUredjivanje(@PathVariable Long id, Model model) {
        Optional<Radnik> radnik = radnikService.findById(id);
        if (radnik.isPresent()) {
            model.addAttribute("radnik", radnik.get());
            return "/Radnik/uredi-radnika";
        } else {
            // Dodajte odgovarajuću logiku za slučaj kada radnik nije pronađen
            return "error";
        }
    }

    // Uređivanje radnika
    @PostMapping("/uredi-radnika/{id}")
    public String urediRadnika(@PathVariable Long id, @ModelAttribute Radnik radnik) {
        radnik.setId(id);
        radnikRepository.save(radnik);
        return "redirect:/prikazi-sve-radnike";
    }


    // Brisanje radnika
    @GetMapping("/obrisi-radnika/{id}")
    public String obrisiRadnika(@PathVariable Long id) {
        Optional<Radnik> radnik = radnikService.findById(id);
        if (radnik.isPresent()) {
            radnikService.delete(radnik.get());
        }
        return "redirect:/prikazi-sve-radnike"; // Nakon brisanja, preusmjeri na prikaz svih radnika
    }
}