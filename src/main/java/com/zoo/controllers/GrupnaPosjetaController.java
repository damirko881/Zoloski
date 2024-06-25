package com.zoo.controllers;

import com.zoo.models.GrupnaPosjeta;
import com.zoo.models.Radnik;
import com.zoo.repositories.GrupnaPosjetaRepository;
import com.zoo.service.GrupnaPosjetaService;
import com.zoo.service.RadnikService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class GrupnaPosjetaController {
    private final GrupnaPosjetaService grupnaPosjetaService;
    private final RadnikService radnikService;
    private final GrupnaPosjetaRepository grupnaPosjetaRepository;

    @Autowired
    public GrupnaPosjetaController(GrupnaPosjetaService grupnaPosjetaService, RadnikService radnikService, GrupnaPosjetaRepository grupnaPosjetaRepository) {
        this.grupnaPosjetaService = grupnaPosjetaService;
        this.radnikService = radnikService;
        this.grupnaPosjetaRepository = grupnaPosjetaRepository;
    }

    @GetMapping("/dodaj-grupnu-posjetu")
    public String prikaziFormuDodajGrupnuPosjetu(Model model) {
        List<Radnik> radnici = radnikService.findAll();
        model.addAttribute("radnici", radnici);
        model.addAttribute("grupnaPosjeta", new GrupnaPosjeta());
        return "/GrupnaPosjeta/dodaj-grupnu-posjetu";
    }

    @PostMapping("/dodaj-grupnu-posjetu")
    public String dodajGrupnuPosjetu(@ModelAttribute GrupnaPosjeta grupnaPosjeta, Model model) {
        try {
            grupnaPosjetaService.save(grupnaPosjeta);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            List<Radnik> radnici = radnikService.findAll();
            model.addAttribute("radnici", radnici);
            model.addAttribute("grupnaPosjeta", grupnaPosjeta);
            return "/GrupnaPosjeta/dodaj-grupnu-posjetu";
        }
        return "redirect:/dashboard";
    }
    @GetMapping("/prikazi-grupne-posjete")
    public String prikaziGrupnePosjete(Model model) {
        List<GrupnaPosjeta> grupnePosjete = grupnaPosjetaService.findAll();
        Map<LocalDate, List<GrupnaPosjeta>> grupnePosjetePoDatumu = grupnePosjete.stream()
                .collect(Collectors.groupingBy(posjeta -> posjeta.getVrijemePocetka().toLocalDate()));
        model.addAttribute("grupnePosjetePoDatumu", grupnePosjetePoDatumu);
        return "/GrupnaPosjeta/prikazi-grupne-posjete";
    }
    @GetMapping("/obrisi-grupnu-posjetu/{id}")
    public String obrisiGrupnuPosjetu(@PathVariable Long id) {
        Optional<GrupnaPosjeta> grupnaPosjeta = grupnaPosjetaRepository.findById(id);
        if (grupnaPosjeta.isPresent()) {
            grupnaPosjetaService.delete(grupnaPosjeta.get());
        }
        return "redirect:/prikazi-grupne-posjete"; // Nakon brisanja, preusmjeri na prikaz svih životinja
    }
    // Prikaz forme za uređivanje životinje
    @GetMapping("/uredi-grupnu-posjetu/{id}")
    public String urediGrupnuPosjetu(@PathVariable Long id, Model model) {
        List<Radnik> radnici = radnikService.findAll();
        model.addAttribute("radnici", radnici);
        Optional<GrupnaPosjeta> grupnaPosjeta = grupnaPosjetaService.findById(id);
        model.addAttribute("grupnaPosjeta", grupnaPosjeta.get());
        return "/GrupnaPosjeta/uredi-grupnu-posjetu"; // Navigacija na stranicu za uređivanje nastambe
    }

    @PostMapping("/uredi-grupnu-posjetu/{id}")
    public String spremiPromjeneGrupnePosjete(@PathVariable Long id, @ModelAttribute("grupnaPosjeta") @Valid GrupnaPosjeta grupnaPosjeta, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/GrupnaPosjeta/uredi-grupnu-posjetu"; // Ako ima grešaka u unosu, ostani na istoj stranici
        }

        try {
            grupnaPosjeta.setId(id); // Postavljanje ID-a grupne posjete kako bi se izvršilo ažuriranje
            grupnaPosjetaService.save(grupnaPosjeta); // Spremi promjene
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<Radnik> radnici = radnikService.findAll();
            model.addAttribute("radnici", radnici);
            model.addAttribute("grupnaPosjeta", grupnaPosjeta);
            return "/GrupnaPosjeta/uredi-grupnu-posjetu";
        }

        return "redirect:/prikazi-grupne-posjete"; // Preusmjeri na prikaz grupnih posjeta nakon spremanja promjena
    }
}