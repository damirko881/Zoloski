package com.zoo.controllers;

import com.zoo.models.Radnik;
import com.zoo.models.RadnoVrijeme;
import com.zoo.service.RadnikService;
import com.zoo.service.RadnoVrijemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RadnoVrijemeController {

    private final RadnoVrijemeService radnoVrijemeService;
    private final RadnikService radnikService;

    @Autowired
    public RadnoVrijemeController(RadnoVrijemeService radnoVrijemeService, RadnikService radnikService) {
        this.radnoVrijemeService = radnoVrijemeService;
        this.radnikService = radnikService;
    }

    @GetMapping("/dodaj-radno-vrijeme")
    public String prikaziFormuDodajRadnoVrijeme(Model model) {
        List<Radnik> radnici = radnikService.findAll();
        model.addAttribute("radnici", radnici);
        model.addAttribute("radnoVrijeme", new RadnoVrijeme());
        return "/RadnoVrijeme/dodaj-radno-vrijeme";
    }

    @PostMapping("/dodaj-radno-vrijeme")
    public String dodajRadnoVrijeme(@ModelAttribute RadnoVrijeme radnoVrijeme, Model model) {
        boolean slobodan = radnoVrijemeService.isRadnikSlobodan(
                radnoVrijeme.getRadnik().getId(),
                radnoVrijeme.getDatum(),
                radnoVrijeme.getPocetak(),
                radnoVrijeme.getKraj()
        );

        if (!slobodan) {
            List<Radnik> radnici = radnikService.findAll();
            model.addAttribute("radnici", radnici);
            model.addAttribute("radnoVrijeme", radnoVrijeme);
            model.addAttribute("error", "Radnik već ima radno vrijeme u navedenom vremenskom intervalu.");
            return "/RadnoVrijeme/dodaj-radno-vrijeme";
        }

        radnoVrijemeService.save(radnoVrijeme);
        return "redirect:/dashboard";
    }
    @GetMapping("/prikazi-sva-radna-vremena")
    public String prikaziSvaRadnaVremena(Model model) {
        LocalDate danas = LocalDate.now();
        List<RadnoVrijeme> radnaVremena = radnoVrijemeService.findAll()
                .stream()
                .filter(rv -> !rv.getDatum().isBefore(danas))
                .collect(Collectors.toList());

        Map<LocalDate, List<RadnoVrijeme>> radnaVremenaPoDatumu = radnaVremena.stream()
                .collect(Collectors.groupingBy(RadnoVrijeme::getDatum));

        model.addAttribute("radnaVremenaPoDatumu", radnaVremenaPoDatumu);
        return "/RadnoVrijeme/prikazi-sva-radna-vremena";
    }
    @GetMapping("/obrisi-radno-vrijeme/{id}")
    public String obrisiRadnoVrijeme(@PathVariable Long id) {
        Optional<RadnoVrijeme> radnoVrijeme = radnoVrijemeService.findById(id);
        if (radnoVrijeme.isPresent()) {
            radnoVrijemeService.delete(radnoVrijeme.get());
        }
        return "redirect:/prikazi-sva-radna-vremena"; // Nakon brisanja, preusmjeri na prikaz radnog vremena
    }
    @GetMapping("/uredi-radno-vrijeme/{id}")
    public String prikaziFormuZaUredjivanje(@PathVariable Long id, Model model) {
        Optional<RadnoVrijeme> radnoVrijeme = radnoVrijemeService.findById(id);
        if (radnoVrijeme.isPresent()) {
            model.addAttribute("radnoVrijeme", radnoVrijeme.get());
            return "/RadnoVrijeme/uredi-radno-vrijeme";
        } else {
            // Dodajte odgovarajuću logiku za slučaj kada radno vrijeme nije pronađeno
            return "error";
        }
    }

    @PostMapping("/uredi-radno-vrijeme/{id}")
    public String urediRadnoVrijeme(@PathVariable Long id, @ModelAttribute RadnoVrijeme radnoVrijeme, Model model) {
        Optional<RadnoVrijeme> postojeceRadnoVrijeme = radnoVrijemeService.findById(id);
        if (postojeceRadnoVrijeme.isPresent()) {
            RadnoVrijeme postojece = postojeceRadnoVrijeme.get();
            boolean slobodan = radnoVrijemeService.isRadnikSlobodan(
                    postojece.getRadnik().getId(),
                    radnoVrijeme.getDatum(),
                    radnoVrijeme.getPocetak(),
                    radnoVrijeme.getKraj()
            );

            if (!slobodan) {
                model.addAttribute("error", "Radnik već ima radno vrijeme u navedenom vremenskom intervalu.");
                model.addAttribute("radnoVrijeme", radnoVrijeme);
                return "/RadnoVrijeme/uredi-radno-vrijeme";
            }

            // Postavite nove vrijednosti radnog vremena
            postojece.setDatum(radnoVrijeme.getDatum());
            postojece.setPocetak(radnoVrijeme.getPocetak());
            postojece.setKraj(radnoVrijeme.getKraj());
            radnoVrijemeService.save(postojece); // Spremi promjene
            return "redirect:/prikazi-sva-radna-vremena"; // Nakon uređivanja, preusmjeri na prikaz radnog vremena
        } else {
            // Dodajte odgovarajuću logiku za slučaj kada radno vrijeme nije pronađeno
            return "error";
        }
    }
}