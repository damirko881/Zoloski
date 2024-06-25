package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RadnoVrijeme {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "radnik_id", nullable = false)
    private Radnik radnik;

    @NotNull(message = "Datum je obvezan")
    private LocalDate datum;
    @NotNull(message = "Početak radnog vremena je obvezan")
    private LocalTime pocetak;

    @NotNull(message = "Kraj radnog vremena je obvezan")
    private LocalTime kraj;

    public RadnoVrijeme() {
    }

    public RadnoVrijeme(Long id, Radnik radnik, LocalDate datum, LocalTime pocetak, LocalTime kraj) {
        this.id = id;
        this.radnik = radnik;
        this.datum = datum;
        this.pocetak = pocetak;
        this.kraj = kraj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
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
    @OneToMany(mappedBy = "radnoVrijeme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Obaveza> obaveze = new ArrayList<>();

    public List<Obaveza> getObaveze() {
        return obaveze;
    }

    public void setObaveze(List<Obaveza> obaveze) {
        this.obaveze = obaveze;
    }

    public void addObaveza(Obaveza obaveza) {
        obaveze.add(obaveza);
        obaveza.setRadnoVrijeme(this);
    }

    public void removeObaveza(Obaveza obaveza) {
        obaveze.remove(obaveza);
        obaveza.setRadnoVrijeme(null);
    }

}
