package com.zoo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Trosak {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Datum je obvezan")
    private LocalDate datum;

    @NotNull(message = "Tip troška je obvezan")
    @Enumerated(EnumType.STRING)
    private TipTroska tipTroska;

    @NotNull(message = "Iznos je obvezan")
    private BigDecimal iznos;

    @ManyToOne
    @JoinColumn(name = "zivotinja_id", nullable = true)
    private Zivotinja zivotinja;

    public Trosak() {
    }

    public Trosak(LocalDate datum, TipTroska tipTroska, BigDecimal iznos, Zivotinja zivotinja) {
        this.datum = datum;
        this.tipTroska = tipTroska;
        this.iznos = iznos;
        this.zivotinja = zivotinja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public TipTroska getTipTroska() {
        return tipTroska;
    }

    public void setTipTroska(TipTroska tipTroska) {
        this.tipTroska = tipTroska;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public Zivotinja getZivotinja() {
        return zivotinja;
    }

    public void setZivotinja(Zivotinja zivotinja) {
        this.zivotinja = zivotinja;
    }
}