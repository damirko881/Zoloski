package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Zivotinja {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="Polje Hrvatski naziv je obvezno")
    String nazivHr;

    @NotBlank(message="Polje Engleski naziv je obvezno")
    String nazivEn;

    @NotBlank(message="Polje Latinski naziv je obvezno")
    String nazivL;
    @NotBlank(message="Polje ime je obvezno")
    String ime;

    Long kolicina;
    @NotBlank(message="Polje dobavljanje je obvezno")
    String dobavljanje;

    @NotNull(message="Polje ime je obvezno")
    Boolean uginuce;

    @ManyToOne
    @JoinColumn(name="nastamba_id")
    private Nastamba nastamba;
    @OneToMany(mappedBy = "zivotinja", cascade = CascadeType.REMOVE)
    private List<Obaveza> obaveze = new ArrayList<>();
    @OneToMany(mappedBy = "zivotinja", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trosak> troskovi = new ArrayList<>();
    @OneToMany(mappedBy = "zivotinja", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Incident> incidenti = new ArrayList<>();
    public Zivotinja() {
    }

    public Zivotinja(Long id, String nazivHr, String nazivEn, String nazivL, String ime, Long kolicina, String dobavljanje, Boolean uginuce, Nastamba nastamba) {
        this.id = id;
        this.nazivHr = nazivHr;
        this.nazivEn = nazivEn;
        this.nazivL = nazivL;
        this.ime = ime;
        this.kolicina = kolicina;
        this.dobavljanje = dobavljanje;
        this.uginuce = uginuce;
        this.nastamba = nastamba;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivHr() {
        return nazivHr;
    }

    public void setNazivHr(String nazivHr) {
        this.nazivHr = nazivHr;
    }

    public String getNazivEn() {
        return nazivEn;
    }

    public void setNazivEn(String nazivEn) {
        this.nazivEn = nazivEn;
    }

    public String getNazivL() {
        return nazivL;
    }

    public void setNazivL(String nazivL) {
        this.nazivL = nazivL;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Long getKolicina() {
        return kolicina;
    }

    public void setKolicina(Long kolicina) {
        this.kolicina = kolicina;
    }

    public String getDobavljanje() {
        return dobavljanje;
    }

    public void setDobavljanje(String dobavljanje) {
        this.dobavljanje = dobavljanje;
    }

    public Boolean getUginuce() {
        return uginuce;
    }

    public void setUginuce(Boolean uginuce) {
        this.uginuce = uginuce;
    }

    public Nastamba getNastamba() {
        return nastamba;
    }

    public void setNastamba(Nastamba nastamba) {
        this.nastamba = nastamba;
    }
    public List<Trosak> getTroskovi() {
        return troskovi;
    }

    public void setTroskovi(List<Trosak> troskovi) {
        this.troskovi = troskovi;
    }

    public void addTrosak(Trosak trosak) {
        troskovi.add(trosak);
        trosak.setZivotinja(this);
    }

    public void removeTrosak(Trosak trosak) {
        troskovi.remove(trosak);
        trosak.setZivotinja(null);
    }

    public List<Obaveza> getObaveze() {
        return obaveze;
    }

    public void setObaveze(List<Obaveza> obaveze) {
        this.obaveze = obaveze;
    }

    public List<Incident> getIncidenti() {
        return incidenti;
    }

    public void setIncidenti(List<Incident> incidenti) {
        this.incidenti = incidenti;
    }
}
