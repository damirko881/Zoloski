package com.zoo.controllers;

import com.zoo.models.Nastamba;
import com.zoo.service.NastambaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class NastambaController {
    @Autowired
    private NastambaService nastambaService;

    @GetMapping("/dodaj-nastambu")
    public String prikaziFormuDodajNastambu(Model model) {
        model.addAttribute("nastamba", new Nastamba());
        return "/Nastamba/dodaj-nastambu";
    }

    @PostMapping("/dodaj-nastambu")
    public String dodajNastambu(@ModelAttribute("nastamba") @Valid Nastamba nastamba, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/Nastamba/dodaj-nastambu";
        }
        nastambaService.save(nastamba);
        return "redirect:/dashboard"; // preusmjeri na dashboard nakon dodavanja
    }
    @GetMapping("/prikazi-nastambu")
    public String prikaziNastambu(Model model) {
        List<Nastamba> nastambe = nastambaService.findAll();
        model.addAttribute("nastambe", nastambe);
        return "/Nastamba/prikazi-nastambu";
    }
    @GetMapping("/nastamba/{id}")
    public String prikaziNastambu(@PathVariable Long id, Model model) {
        Optional<Nastamba> nastamba = nastambaService.findById(id);
        model.addAttribute("nastamba", nastamba);
        return "/Nastamba/nastamba-info";
    }

    @GetMapping("/obrisi-nastambu/{id}")
    public String potvrdaBrisanjaNastambe(@PathVariable Long id) {
        Optional<Nastamba> nastamba = nastambaService.findById(id);
        if (nastamba.isPresent()) {
            nastambaService.delete(nastamba.get());
        }
        return "redirect:/prikazi-nastambu"; // Nakon brisanja, preusmjeri na prikaz nastambi
    }
    @GetMapping("/uredi-nastambu/{id}")
    public String urediNastambu(@PathVariable Long id, Model model) {
        Optional<Nastamba> nastamba = nastambaService.findById(id);
        if (nastamba.isPresent()) {
            model.addAttribute("nastamba", nastamba.get());
            return "/Nastamba/uredi-nastambu"; // Navigacija na stranicu za uređivanje nastambe
        } else {
            return "redirect:/prikazi-nastambu"; // Ako nastamba nije pronađena, preusmjeri na prikaz nastambi
        }
    }

    @PostMapping("/uredi-nastambu/{id}")
    public String spremiPromjeneNastambe(@PathVariable Long id, @ModelAttribute("nastamba") @Valid Nastamba nastamba, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/Nastamba/uredi-nastambu"; // Ako ima grešaka u unosu, ostani na istoj stranici
        }
        nastamba.setId(id); // Postavljanje ID-a nastambe kako bi se izvršilo ažuriranje
        nastambaService.save(nastamba); // Spremi promjene
        return "redirect:/prikazi-nastambu"; // Preusmjeri na prikaz nastambi nakon spremanja promjena
    }
}