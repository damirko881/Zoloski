package com.zoo.service;

import com.zoo.models.RadnoVrijeme;
import com.zoo.repositories.RadnoVrijemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RadnoVrijemeService {

    private final RadnoVrijemeRepository radnoVrijemeRepository;

    @Autowired
    public RadnoVrijemeService(RadnoVrijemeRepository radnoVrijemeRepository) {
        this.radnoVrijemeRepository = radnoVrijemeRepository;
    }

    public List<RadnoVrijeme> findAll() {
        return radnoVrijemeRepository.findAll();
    }

    public Optional<RadnoVrijeme> findById(Long id) {
        return radnoVrijemeRepository.findById(id);
    }

    public RadnoVrijeme save(RadnoVrijeme radnoVrijeme) {
        return radnoVrijemeRepository.save(radnoVrijeme);
    }

    public void delete(RadnoVrijeme radnoVrijeme) {
        radnoVrijemeRepository.delete(radnoVrijeme);
    }
    // Metoda za provjeru dostupnosti radnika u određenom vremenskom intervalu
    public boolean isRadnikSlobodan(Long radnikId, LocalDate datum, LocalTime pocetak, LocalTime kraj) {
        List<RadnoVrijeme> radnoVrijemeList = radnoVrijemeRepository.findByRadnikIdAndDatum(radnikId, datum);
        for (RadnoVrijeme radnoVrijeme : radnoVrijemeList) {
            // Provjera preklapanja vremenskih intervala
            if ((radnoVrijeme.getKraj().isAfter(pocetak) || radnoVrijeme.getKraj().equals(pocetak)) &&
                    (radnoVrijeme.getPocetak().isBefore(kraj) || radnoVrijeme.getPocetak().equals(kraj))) {
                return false; // Radnik nije slobodan
            }
        }
        return true; // Radnik je slobodan
    }
}