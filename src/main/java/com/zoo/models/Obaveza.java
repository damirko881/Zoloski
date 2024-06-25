package com.zoo.models;

import com.zoo.models.RadnoVrijeme;
import com.zoo.models.Zivotinja;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

@Entity
public class Obaveza {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String naziv;
    @NotNull(message = "Vrijeme početka je obavezno")
    private LocalTime pocetak;

    @NotNull(message = "Vrijeme kraja je obavezno")
    private LocalTime kraj;

    @ManyToOne
    @JoinColumn(name = "radno_vrijeme_id")
    private RadnoVrijeme radnoVrijeme;

    @ManyToOne
    @JoinColumn(name = "zivotinja_id", nullable = true)
    private Zivotinja zivotinja;

    public Obaveza() {
    }

    public Obaveza(Long id, String naziv, LocalTime pocetak, LocalTime kraj, RadnoVrijeme radnoVrijeme, Zivotinja zivotinja) {
        this.id = id;
        this.naziv=naziv;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.radnoVrijeme = radnoVrijeme;
        this.zivotinja = zivotinja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public LocalTime getPocetak() {
        return pocetak;
    }

    public void setPocetak(LocalTime pocetak) {
        this.pocetak = pocetak;
    }

    public LocalTime getKraj() {
        return kraj;
    }

    public void setKraj(LocalTime kraj) {
        this.kraj = kraj;
    }

    public RadnoVrijeme getRadnoVrijeme() {
        return radnoVrijeme;
    }

    public void setRadnoVrijeme(RadnoVrijeme radnoVrijeme) {
        this.radnoVrijeme = radnoVrijeme;
    }

    public Zivotinja getZivotinja() {
        return zivotinja;
    }

    public void setZivotinja(Zivotinja zivotinja) {
        this.zivotinja = zivotinja;
    }
}