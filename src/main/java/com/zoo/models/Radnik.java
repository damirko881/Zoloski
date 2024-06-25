package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Radnik {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Polje ime je obvezno")
    String ime;
    @NotBlank(message = "Polje prezime je obvezno")
    String prezime;
    @NotBlank(message = "Polje kontakt je obvezno")
    String kontakt;
    @NotBlank(message = "Polje obrazovanje je obvezno")
    String obrazovanje;

    @OneToMany(mappedBy = "radnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RadnoVrijeme> radnaVremena = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "grupna_posjeta_radnici",
            joinColumns = @JoinColumn(name = "radnik_id"),
            inverseJoinColumns = @JoinColumn(name = "grupna_posjeta_id")
    )
    private Set<GrupnaPosjeta> grupnePosjete = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "radnik_osposobljavanje",
            joinColumns = @JoinColumn(name = "radnik_id"),
            inverseJoinColumns = @JoinColumn(name = "osposobljavanje_id")
    )
    private Set<Osposobljavanje> osposobljavanja = new HashSet<>();

    public Radnik() {
    }

    public Radnik(Long id, String ime, String prezime, String kontakt, String obrazovanje) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.kontakt = kontakt;
        this.obrazovanje = obrazovanje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getObrazovanje() {
        return obrazovanje;
    }

    public void setObrazovanje(String obrazovanje) {
        this.obrazovanje = obrazovanje;
    }

    public List<RadnoVrijeme> getRadnaVremena() {
        return radnaVremena;
    }

    public void setRadnaVremena(List<RadnoVrijeme> radnaVremena) {
        this.radnaVremena = radnaVremena;
    }

    public void addRadnoVrijeme(RadnoVrijeme radnoVrijeme) {
        radnaVremena.add(radnoVrijeme);
        radnoVrijeme.setRadnik(this);
    }

    public void removeRadnoVrijeme(RadnoVrijeme radnoVrijeme) {
        radnaVremena.remove(radnoVrijeme);
        radnoVrijeme.setRadnik(null);

    }
    public Set<Osposobljavanje> getOsposobljavanja() {
        return osposobljavanja;
    }

    public void setOsposobljavanja(Set<Osposobljavanje> osposobljavanja) {
        this.osposobljavanja = osposobljavanja;
    }
}
