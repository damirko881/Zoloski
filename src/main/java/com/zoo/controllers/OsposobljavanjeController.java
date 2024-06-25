package com.zoo.controllers;

import com.zoo.models.Osposobljavanje;
import com.zoo.service.OsposobljavanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class OsposobljavanjeController {

    private final OsposobljavanjeService osposobljavanjeService;

    @Autowired
    public OsposobljavanjeController(OsposobljavanjeService osposobljavanjeService) {
        this.osposobljavanjeService = osposobljavanjeService;
    }

    @GetMapping("/dodaj-osposobljavanje")
    public String prikaziFormuDodajOsposobljavanje(Model model) {
        model.addAttribute("osposobljavanje", new Osposobljavanje());
        return "/Osposobljavanje/dodaj-osposobljavanje";
    }

    @PostMapping("/dodaj-osposobljavanje")
    public String dodajOsposobljavanje(@ModelAttribute Osposobljavanje osposobljavanje) {
        osposobljavanjeService.save(osposobljavanje);
        return "redirect:/dashboard";
    }
    @GetMapping("/prikazi-osposobljavanja")
    public String prikaziOsposobljavanja(Model model) {
        List<Osposobljavanje> osposobljavanja = osposobljavanjeService.findAll();
        model.addAttribute("osposobljavanja", osposobljavanja);
        return "/Osposobljavanje/prikazi-osposobljavanja";
    }
    @GetMapping("/obrisi-osposobljavanje/{id}")
    public String obrisiOsposobljavanje(@PathVariable Long id) {
        Optional<Osposobljavanje> osposobljavanje = osposobljavanjeService.findById(id);
        if (osposobljavanje.isPresent()) {
            osposobljavanjeService.delete(osposobljavanje.get());
        }
        return "redirect:/prikazi-osposobljavanja";
    }
}