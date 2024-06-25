package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Osposobljavanje {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="Polje naziv je obvezno")
    private String naziv;
    @ManyToMany(mappedBy = "osposobljavanja", cascade = CascadeType.REMOVE)
    private Set<Radnik> radnici = new HashSet<>();

    public Osposobljavanje() {
    }

    public Osposobljavanje(String naziv) {
        this.naziv = naziv;
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
    public Set<Radnik> getRadnici() {
        return radnici;
    }

    public void setRadnici(Set<Radnik> radnici) {
        this.radnici = radnici;
    }

}
