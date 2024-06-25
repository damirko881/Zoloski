package com.zoo.service;

import com.zoo.models.Obaveza;
import com.zoo.models.Osposobljavanje;
import com.zoo.models.Radnik;
import com.zoo.models.RadnoVrijeme;
import com.zoo.repositories.RadnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

@Service
public class RadnikService {
    @Autowired
    private RadnikRepository radnikRepository;

    @Autowired
    public RadnikService(RadnikRepository radnikRepository) {
        this.radnikRepository = radnikRepository;
    }

    public List<Radnik> findAll() {
        return radnikRepository.findAll();
    }

    public Optional<Radnik> findById(Long id) {
        return radnikRepository.findById(id);
    }

    public Radnik save(Radnik radnik) {
        return radnikRepository.save(radnik);
    }

    public void delete(Radnik radnik) {
        radnikRepository.delete(radnik);
    }

    public boolean hasObavezeInInterval(LocalDateTime pocetak, LocalDateTime kraj, Long radnikId) {
        List<RadnoVrijeme> radnoVrijemeRadnika = radnikRepository.findById(radnikId).get().getRadnaVremena();
        for (RadnoVrijeme rv : radnoVrijemeRadnika) {
            if ((pocetak.isEqual(rv.getDatum().atTime(rv.getPocetak())) &&
                    kraj.isEqual(rv.getDatum().atTime(rv.getKraj()))) ||
                    (pocetak.isAfter(rv.getDatum().atTime(rv.getPocetak())) &&
                            kraj.isBefore(rv.getDatum().atTime(rv.getKraj())))) {
                return true;
            }
        }
        return false;
    }

}