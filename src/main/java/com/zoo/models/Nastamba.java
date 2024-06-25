package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Nastamba {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="Polje naziv je obvezno")
    String naziv;

    @NotBlank(message="Polje geometrijski opis je obvezno")
    String geomOpis;

    @NotBlank(message="Polje pozicija je obvezno")
    String pozicija;

    @OneToMany(mappedBy = "nastamba", cascade = CascadeType.REMOVE)
    private List<Zivotinja> zivotinje = new ArrayList<>();
    @OneToMany(mappedBy = "nastamba", cascade = CascadeType.REMOVE)
    private List<Incident> incidenti = new ArrayList<>();
    public Nastamba() {
    }

    public Nastamba(Long id, String naziv, String geomOpis, String pozicija) {
        this.id = id;
        this.naziv = naziv;
        this.geomOpis = geomOpis;
        this.pozicija = pozicija;
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

    public String getGeomOpis() {
        return geomOpis;
    }

    public void setGeomOpis(String geomOpis) {
        this.geomOpis = geomOpis;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }
    public List<Zivotinja> getZivotinje() {
        return zivotinje;
    }

    public void setZivotinje(List<Zivotinja> zivotinje) {
        this.zivotinje = zivotinje;
    }

    public void addZivotinja(Zivotinja zivotinja) {
        zivotinje.add(zivotinja);
        zivotinja.setNastamba(this);
    }

    public void removeZivotinja(Zivotinja zivotinja) {
        zivotinje.remove(zivotinja);
        zivotinja.setNastamba(null);
    }

    public List<Incident> getIncidenti() {
        return incidenti;
    }

    public void setIncidenti(List<Incident> incidenti) {
        this.incidenti = incidenti;
    }
}
