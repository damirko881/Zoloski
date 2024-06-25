package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Korisnik {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="Polje korisničko ime je obvezno")
    private String korisnickoIme;
    @ElementCollection(fetch= FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Role> roles= new HashSet<>();
    String lozinka;
    public Korisnik() {
    }

    public Korisnik(Long id, String korisnickoIme, String lozinka) {
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    public Set<Role> getRoles(){
        return roles;
    }
}
