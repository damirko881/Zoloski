package com.zoo.service;

import com.zoo.models.Obaveza;
import com.zoo.models.RadnoVrijeme;
import com.zoo.repositories.ObavezaRepository;
import com.zoo.repositories.RadnoVrijemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ObavezaService {

    private final ObavezaRepository obavezaRepository;
    private final RadnoVrijemeRepository radnoVrijemeRepository;

    @Autowired
    public ObavezaService(ObavezaRepository obavezaRepository, RadnoVrijemeRepository radnoVrijemeRepository) {
        this.obavezaRepository = obavezaRepository;
        this.radnoVrijemeRepository = radnoVrijemeRepository;
    }

    public List<Obaveza> findAll() {
        return obavezaRepository.findAll();
    }

    public Optional<Obaveza> findById(Long id) {
        return obavezaRepository.findById(id);
    }

    public Obaveza save(Obaveza obaveza) {
        RadnoVrijeme radnoVrijeme = radnoVrijemeRepository.findById(obaveza.getRadnoVrijeme().getId())
                .orElseThrow(() -> new IllegalArgumentException("Radno vrijeme ne postoji"));

        if (obaveza.getPocetak().isBefore(radnoVrijeme.getPocetak()) || obaveza.getKraj().isAfter(radnoVrijeme.getKraj())) {
            throw new IllegalArgumentException("Obaveza mora biti unutar radnog vremena.");
        }
        if (isRadnikZauzet(radnoVrijeme.getId(), obaveza.getPocetak(), obaveza.getKraj())) {
            throw new IllegalArgumentException("Radnik je već zauzet u tom vremenskom periodu.");
        }
        return obavezaRepository.save(obaveza);
    }

    public void delete(Obaveza obaveza) {
        obavezaRepository.delete(obaveza);
    }

    public boolean isRadnikZauzet(Long radnoVrijemeId, LocalTime pocetak, LocalTime kraj) {
        List<Obaveza> obaveze = obavezaRepository.findByRadnoVrijemeId(radnoVrijemeId);
        for (Obaveza obaveza : obaveze) {
            if ((pocetak.isBefore(obaveza.getKraj()) && pocetak.isAfter(obaveza.getPocetak())) ||
                    (kraj.isBefore(obaveza.getKraj()) && kraj.isAfter(obaveza.getPocetak())) ||
                    (pocetak.equals(obaveza.getPocetak()) || kraj.equals(obaveza.getKraj()))) {
                return true;
            }
        }
        return false;
    }
}