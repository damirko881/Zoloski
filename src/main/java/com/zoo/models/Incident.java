package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Incident {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Vrsta incidenta je obavezna")
    private String vrsta;

    private String komentar;

    // Značajnost incidenta može biti npr. Niska, Srednja, Visoka
    private String znacajnost;

    @NotNull(message = "Troškovi incidenta su obavezni")
    private double troskovi;

    @ManyToOne
    @JoinColumn(name = "nastamba_id")
    private Nastamba nastamba;
    @ManyToOne
    @JoinColumn(name = "zivotinja_id")
    private Zivotinja zivotinja;

    public Incident() {
    }

    public Incident(Long id, String vrsta, String komentar, String znacajnost, double troskovi, Nastamba nastamba, Zivotinja zivotinja) {
        this.id = id;
        this.vrsta = vrsta;
        this.komentar = komentar;
        this.znacajnost = znacajnost;
        this.troskovi = troskovi;
        this.nastamba = nastamba;
        this.zivotinja=zivotinja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getZnacajnost() {
        return znacajnost;
    }

    public void setZnacajnost(String znacajnost) {
        this.znacajnost = znacajnost;
    }

    public double getTroskovi() {
        return troskovi;
    }

    public void setTroskovi(double troskovi) {
        this.troskovi = troskovi;
    }

    public Nastamba getNastamba() {
        return nastamba;
    }

    public void setNastamba(Nastamba nastamba) {
        this.nastamba = nastamba;
    }

    public Zivotinja getZivotinja() {
        return zivotinja;
    }

    public void setZivotinja(Zivotinja zivotinja) {
        this.zivotinja = zivotinja;
    }
    // Getteri i setteri, konstruktori
}