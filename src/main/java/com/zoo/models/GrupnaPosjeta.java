package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class GrupnaPosjeta {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Naziv grupe je obavezan")
    private String nazivGrupe;

    @NotNull(message = "Broj ljudi u grupi je obavezan")
    private int brojLjudiUGrupi;

    private String komentar;

    @ManyToMany
    @JoinTable(name = "grupna_posjeta_radnici",
            joinColumns = @JoinColumn(name = "grupna_posjeta_id"),
            inverseJoinColumns = @JoinColumn(name = "radnik_id"))
    private Set<Radnik> vodici = new HashSet<>();

    @NotNull(message = "Vrijeme početka posjete je obavezno")
    private LocalDateTime vrijemePocetka;

    @NotNull(message = "Vrijeme završetka posjete je obavezno")
    private LocalDateTime vrijemeZavrsetka;

    public GrupnaPosjeta() {
    }

    public GrupnaPosjeta(Long id, String nazivGrupe, int brojLjudiUGrupi, String komentar, Set<Radnik> vodici, LocalDateTime vrijemePocetka, LocalDateTime vrijemeZavrsetka) {
        this.id = id;
        this.nazivGrupe = nazivGrupe;
        this.brojLjudiUGrupi = brojLjudiUGrupi;
        this.komentar = komentar;
        this.vodici = vodici;
        this.vrijemePocetka = vrijemePocetka;
        this.vrijemeZavrsetka = vrijemeZavrsetka;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivGrupe() {
        return nazivGrupe;
    }

    public void setNazivGrupe(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
    }

    public int getBrojLjudiUGrupi() {
        return brojLjudiUGrupi;
    }

    public void setBrojLjudiUGrupi(int brojLjudiUGrupi) {
        this.brojLjudiUGrupi = brojLjudiUGrupi;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Set<Radnik> getVodici() {
        return vodici;
    }

    public void setVodici(Set<Radnik> vodici) {
        this.vodici = vodici;
    }

    public LocalDateTime getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(LocalDateTime vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public LocalDateTime getVrijemeZavrsetka() {
        return vrijemeZavrsetka;
    }

    public void setVrijemeZavrsetka(LocalDateTime vrijemeZavrsetka) {
        this.vrijemeZavrsetka = vrijemeZavrsetka;
    }
    // Getteri i setteri, konstruktori
}