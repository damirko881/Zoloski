package com.zoo.controllers;

import com.zoo.models.Trosak;
import com.zoo.models.TipTroska;
import com.zoo.models.Zivotinja;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TrosakController {

    private final TrosakService trosakService;
    private final ZivotinjaService zivotinjaService;

    @Autowired
    public TrosakController(TrosakService trosakService, ZivotinjaService zivotinjaService) {
        this.trosakService = trosakService;
        this.zivotinjaService = zivotinjaService;
    }

    @GetMapping("/dodaj-trosak")
    public String showCreateForm(Model model) {
        List<Zivotinja> zivotinje = zivotinjaService.findAll();
        model.addAttribute("trosak", new Trosak());
        model.addAttribute("zivotinje", zivotinje);
        model.addAttribute("tipoviTroska", TipTroska.values());
        return "/Trosak/dodaj-trosak";
    }

    @PostMapping("/dodaj-trosak")
    public String createTrosak(@Valid @ModelAttribute("trosak") Trosak trosak, BindingResult result, Model model) {
        // Provjerite je li odabrana životinja
        if (trosak.getZivotinja() != null && trosak.getZivotinja().getId() == null) {
            // Ako nije odabrana, postavite životinju na null
            trosak.setZivotinja(null);
        }

        // Provjerite rezultat validacije
        if (result.hasErrors()) {
            List<Zivotinja> zivotinje = zivotinjaService.findAll();
            model.addAttribute("zivotinje", zivotinje);
            model.addAttribute("tipoviTroska", TipTroska.values());
            return "/Trosak/dodaj-trosak";
        }

        // Postavi datum na trenutni datum
        trosak.setDatum(LocalDate.now());

        // Spremi trošak
        trosakService.save(trosak);

        // Preusmjeri na dashboard
        return "redirect:/dashboard";
    }
    @GetMapping("/prikazi-troskove")
    public String prikaziTroskove(Model model) {
        List<Trosak> troskovi = trosakService.findAll();
        Map<String, List<Trosak>> troskoviPoZivotinjama = troskovi.stream()
                .collect(Collectors.groupingBy(t -> t.getZivotinja() != null ? t.getZivotinja().getIme() : "Bez životinje"));
        model.addAttribute("troskoviPoZivotinjama", troskoviPoZivotinjama);
        return "/Trosak/prikazi-troskove";
    }
    @GetMapping("/uredi-trosak/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Trosak> trosakOpt = trosakService.findById(id);
        if (!trosakOpt.isPresent()) {
            throw new IllegalArgumentException("Nevažeći ID troška: " + id);
        }
        Trosak trosak = trosakOpt.get();
        model.addAttribute("trosak", trosak);
        return "/Trosak/uredi-trosak";
    }

    @PostMapping("/uredi-trosak/{id}")
    public String updateTrosak(@PathVariable("id") Long id, @Valid @ModelAttribute("trosak") Trosak trosak, BindingResult result, Model model) {
        if (result.hasErrors()) {
            trosak.setId(id);
            return "/Trosak/uredi-trosak";
        }
        trosakService.save(trosak);
        return "redirect:/prikazi-troskove";
    }

    @GetMapping("/obrisi-trosak/{id}")
    public String deleteTrosak(@PathVariable("id") Long id) {
        trosakService.deleteById(id);
        return "redirect:/prikazi-troskove";
    }
}