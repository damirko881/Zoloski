package com.zoo.controllers;

import com.zoo.models.Korisnik;
import com.zoo.repositories.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    KorisnikRepository adminService;

    @GetMapping("/login")
    public String loginget (Model model){
        model.addAttribute("admin", new Korisnik());
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        return "dashboard";
    }
}