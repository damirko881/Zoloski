package com.zoo.controllers;

import com.zoo.models.*;
import com.zoo.repositories.ObavezaRepository;
import com.zoo.service.ObavezaService;
import com.zoo.service.RadnoVrijemeService;
import com.zoo.service.TrosakService;
import com.zoo.service.ZivotinjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ObavezaController {

    private final ObavezaService obavezaService;
    private final RadnoVrijemeService radnoVrijemeService;
    private final ZivotinjaService zivotinjaService;

    private final TrosakService trosakService;
    private final ObavezaRepository obavezaRepository;

    @Autowired
    public ObavezaController(ObavezaService obavezaService, RadnoVrijemeService radnoVrijemeService, ZivotinjaService zivotinjaService, TrosakService trosakService, ObavezaRepository obavezaRepository) {
        this.obavezaService = obavezaService;
        this.radnoVrijemeService = radnoVrijemeService;
        this.zivotinjaService = zivotinjaService;
        this.trosakService = trosakService;
        this.obavezaRepository = obavezaRepository;
    }

    @GetMapping("/dodaj-obavezu")
    public String prikaziFormuDodajObavezu(Model model) {
        LocalDate danas = LocalDate.now();
        List<RadnoVrijeme> radnaVremena = radnoVrijemeService.findAll().stream()
                .filter(rv -> rv.getDatum().isAfter(danas) || rv.getDatum().isEqual(danas))
                .collect(Collectors.toList());
        List<Zivotinja> zivotinje = zivotinjaService.findAll();
        model.addAttribute("radnaVremena", radnaVremena);
        model.addAttribute("zivotinje", zivotinje);
        model.addAttribute("obaveza", new Obaveza());
        return "/Obaveza/dodaj-obavezu";
    }

    @PostMapping("/dodaj-obavezu")
    public String dodajObavezu(@ModelAttribute Obaveza obaveza, Model model) {
        try {
            if (obaveza.getZivotinja() != null && obaveza.getZivotinja().getId() == null) {
                obaveza.setZivotinja(null);
            }
            obavezaService.save(obaveza);

            // Dodaj trošak radnih sati
            Trosak trosak = new Trosak(LocalDate.now(), TipTroska.RADNI_SATI, calculateRadniSatiCost(obaveza), obaveza.getZivotinja());
            trosakService.save(trosak);

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<RadnoVrijeme> radnaVremena = radnoVrijemeService.findAll();
            List<Zivotinja> zivotinje = zivotinjaService.findAll();
            model.addAttribute("radnaVremena", radnaVremena);
            model.addAttribute("zivotinje", zivotinje);
            model.addAttribute("obaveza", obaveza);
            return "/Obaveza/dodaj-obavezu";
        }
        return "redirect:/dashboard";
    }

    // Pomoćna metoda za izračun troška radnih sati
    private BigDecimal calculateRadniSatiCost(Obaveza obaveza) {
        long minutes = Duration.between(obaveza.getPocetak(), obaveza.getKraj()).toMinutes();
        BigDecimal hours = new BigDecimal(minutes).divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
        BigDecimal hourlyRate = new BigDecimal("50.00"); // Pretpostavljena satnica
        return hours.multiply(hourlyRate);
    }

    @GetMapping("/prikazi-sve-obaveze")
    public String prikaziSveObaveze(Model model) {
        LocalDate danas = LocalDate.now();
        List<Obaveza> obaveze = obavezaService.findAll().stream()
                .filter(obaveza -> obaveza.getRadnoVrijeme().getDatum().isAfter(danas) || obaveza.getRadnoVrijeme().getDatum().isEqual(danas))
                .collect(Collectors.toList());

        Map<LocalDate, List<Obaveza>> obavezePoDatumu = obaveze.stream()
                .collect(Collectors.groupingBy(obaveza -> obaveza.getRadnoVrijeme().getDatum()));

        model.addAttribute("obavezePoDatumu", obavezePoDatumu);
        return "/Obaveza/prikazi-sve-obaveze";
    }
    @GetMapping("/obrisi-obavezu/{id}")
    public String obrisiObavezu(@PathVariable Long id) {
        Optional<Obaveza> obaveza = obavezaService.findById(id);
        if (obaveza.isPresent()) {
            obavezaService.delete(obaveza.get());
        }
        return "redirect:/prikazi-sve-obaveze"; // Nakon brisanja, preusmjeri na prikaz svih životinja
    }
    // Prikaz forme za uređivanje životinje
    @GetMapping("/uredi-obavezu/{id}")
    public String prikaziFormuZaUredjivanje(@PathVariable Long id, Model model) {
        List<RadnoVrijeme> radnaVremena = radnoVrijemeService.findAll();
        List<Zivotinja> zivotinje = zivotinjaService.findAll();
        model.addAttribute("radnaVremena", radnaVremena);
        model.addAttribute("zivotinje", zivotinje);
        Optional<Obaveza> obaveza = obavezaService.findById(id);
        model.addAttribute("obaveza", obaveza.orElse(new Obaveza()));
        return "/Obaveza/uredi-obavezu";
    }

    @PostMapping("/uredi-obavezu/{id}")
    public String urediObavezu(@PathVariable Long id, @ModelAttribute Obaveza obaveza, Model model) {
        try {
            obaveza.setId(id);
            obavezaService.save(obaveza);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            List<RadnoVrijeme> radnaVremena = radnoVrijemeService.findAll();
            List<Zivotinja> zivotinje = zivotinjaService.findAll();
            model.addAttribute("radnaVremena", radnaVremena);
            model.addAttribute("zivotinje", zivotinje);
            model.addAttribute("obaveza", obaveza);
            return "/Obaveza/uredi-obavezu";
        }
        return "redirect:/prikazi-sve-obaveze"; // Povratak na prikaz svih obaveza
    }
}