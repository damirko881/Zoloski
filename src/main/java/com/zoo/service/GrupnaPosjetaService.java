package com.zoo.service;

import com.zoo.models.*;
import com.zoo.repositories.GrupnaPosjetaRepository;
import com.zoo.repositories.RadnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zoo.service.ObavezaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GrupnaPosjetaService {

    private final GrupnaPosjetaRepository grupnaPosjetaRepository;
    private final RadnikRepository radnikRepository;
    private final RadnoVrijemeService radnoVrijemeService;
    private final ObavezaService obavezaService;

    @Autowired
    public GrupnaPosjetaService(GrupnaPosjetaRepository grupnaPosjetaRepository, RadnikRepository radnikRepository, RadnoVrijemeService radnoVrijemeService, ObavezaService obavezaService) {
        this.grupnaPosjetaRepository = grupnaPosjetaRepository;
        this.radnikRepository = radnikRepository;
        this.radnoVrijemeService = radnoVrijemeService;
        this.obavezaService = obavezaService;
    }

    public GrupnaPosjeta save(GrupnaPosjeta grupnaPosjeta) {
        // Provjeravamo dostupnost radnika kao vodiča za grupnu posjetu
        for (Radnik vodic : grupnaPosjeta.getVodici()) {
            if (!isRadnikDostupan(grupnaPosjeta.getVrijemePocetka(), grupnaPosjeta.getVrijemeZavrsetka(), vodic)) {
                throw new IllegalArgumentException("Radnik " + vodic.getIme() + " " + vodic.getPrezime() + " nije dostupan u zadatom vremenskom intervalu.");
            }
        }
        return grupnaPosjetaRepository.save(grupnaPosjeta);
    }

    private boolean isRadnikDostupan(LocalDateTime pocetak, LocalDateTime kraj, Radnik radnik) {
        List<RadnoVrijeme> radnoVrijemeRadnika = radnik.getRadnaVremena();
        for (RadnoVrijeme rv : radnoVrijemeRadnika) {
            LocalDateTime pocetakRadnogVremena = rv.getDatum().atTime(rv.getPocetak());
            LocalDateTime krajRadnogVremena = rv.getDatum().atTime(rv.getKraj());

            if (pocetak.isEqual(pocetakRadnogVremena) || pocetak.isAfter(pocetakRadnogVremena)) {
                if (kraj.isEqual(krajRadnogVremena) || kraj.isBefore(krajRadnogVremena)) {
                    if (!obavezaService.isRadnikZauzet(rv.getId(), pocetak.toLocalTime(), kraj.toLocalTime())) {
                        // Ako nema obavezu, dodajemo obavezu za to vrijeme
                        Obaveza novaObaveza = new Obaveza();
                        novaObaveza.setNaziv("Grupna posjeta");
                        novaObaveza.setPocetak(pocetak.toLocalTime());
                        novaObaveza.setKraj(kraj.toLocalTime());
                        novaObaveza.setRadnoVrijeme(rv);
                        obavezaService.save(novaObaveza);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Optional<GrupnaPosjeta> findById(Long id) {
        return grupnaPosjetaRepository.findById(id);
    }
    public List<GrupnaPosjeta> findAll() {
        return grupnaPosjetaRepository.findAll();
    }
    public void delete(GrupnaPosjeta grupnaPosjeta) {
        grupnaPosjetaRepository.delete(grupnaPosjeta);
    }

}