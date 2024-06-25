package com.zoo.controllers;

import com.zoo.models.Nastamba;
import com.zoo.models.Zivotinja;
import com.zoo.repositories.NastambaRepository;
import com.zoo.repositories.ZivotinjaRepository;
import com.zoo.service.ZivotinjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ZivotinjaController {

    private final ZivotinjaRepository zivotinjaRepository;
    private final NastambaRepository nastambaRepository;

    private final ZivotinjaService zivotinjaService;


    @Autowired
    public ZivotinjaController(ZivotinjaRepository zivotinjaRepository, NastambaRepository nastambaRepository, ZivotinjaService zivotinjaService) {
        this.zivotinjaRepository = zivotinjaRepository;
        this.nastambaRepository = nastambaRepository;
        this.zivotinjaService = zivotinjaService;
    }

    @GetMapping("/dodaj-zivotinju")
    public String prikaziFormuDodajZivotinju(Model model) {
        List<Nastamba> nastambe = nastambaRepository.findAll();
        model.addAttribute("nastambe", nastambe);
        model.addAttribute("zivotinja", new Zivotinja());
        return "/Zivotinja/dodaj-zivotinju";
    }

    @PostMapping("/dodaj-zivotinju")
    public String dodajZivotinju(@ModelAttribute Zivotinja zivotinja) {
        zivotinja.setUginuce(Boolean.FALSE);
        zivotinjaRepository.save(zivotinja);
        return "redirect:/dashboard"; // Podesite URL na koji želite preusmjeriti nakon dodavanja životinje
    }
    @GetMapping("/zivotinja/{id}")
    public String prikaziZivotinju(@PathVariable Long id, Model model) {
        Optional<Zivotinja> zivotinja = zivotinjaService.findById(id);
        if (zivotinja.isPresent()) {
            BigDecimal ukupniTroskovi = zivotinjaService.ukupniTroskoviZaZivotinju(id);
            model.addAttribute("zivotinja", zivotinja.get());
            model.addAttribute("ukupniTroskovi", ukupniTroskovi);
            return "/Zivotinja/prikazi-zivotinju";
        } else {
            // Dodajte odgovarajuću logiku za slučaj kada životinja nije pronađena
            return "error";
        }
    }
    @GetMapping("/prikazi-sve-zivotinje")
    public String prikaziZivotinje(Model model) {
        List<Zivotinja> zivotinje = zivotinjaService.findAll();
        // Izračunaj ukupne troškove za svaku životinju
        Map<Long, BigDecimal> ukupniTroskoviMap = new HashMap<>();
        for (Zivotinja zivotinja : zivotinje) {
            BigDecimal ukupniTroskovi = zivotinjaService.ukupniTroskoviZaZivotinju(zivotinja.getId());
            ukupniTroskoviMap.put(zivotinja.getId(), ukupniTroskovi);
        }
        model.addAttribute("zivotinje", zivotinje);
        model.addAttribute("ukupniTroskoviMap", ukupniTroskoviMap);

        return "/Zivotinja/prikazi-sve-zivotinje";
    }
    @GetMapping("/obrisi-zivotinju/{id}")
    public String obrisiZivotinju(@PathVariable Long id) {
        Optional<Zivotinja> zivotinja = zivotinjaService.findById(id);
        if (zivotinja.isPresent()) {
            zivotinjaService.delete(zivotinja.get());
        }
        return "redirect:/prikazi-sve-zivotinje"; // Nakon brisanja, preusmjeri na prikaz svih životinja
    }
    // Prikaz forme za uređivanje životinje
    @GetMapping("/uredi-zivotinju/{id}")
    public String prikaziFormuZaUredjivanje(@PathVariable Long id, Model model) {
        List<Nastamba> nastambe = nastambaRepository.findAll();
        model.addAttribute("nastambe", nastambe);
        Optional<Zivotinja> zivotinja = zivotinjaService.findById(id);
        model.addAttribute("zivotinja", zivotinja.orElse(new Zivotinja()));
        return "/Zivotinja/uredi-zivotinju";
    }

    // Ažuriranje životinje
    @PostMapping("/uredi-zivotinju/{id}")
    public String urediZivotinju(@PathVariable Long id, @ModelAttribute Zivotinja zivotinja) {
        zivotinja.setId(id);
        zivotinjaRepository.save(zivotinja);
        return "redirect:/prikazi-sve-zivotinje"; // Povratak na prikaz svih životinja
    }
}
