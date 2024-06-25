package com.zoo.service;

import com.zoo.models.Zivotinja;
import com.zoo.repositories.ZivotinjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ZivotinjaService {

    private final ZivotinjaRepository zivotinjaRepository;

    @Autowired
    public ZivotinjaService(ZivotinjaRepository zivotinjaRepository) {
        this.zivotinjaRepository = zivotinjaRepository;
    }

    public List<Zivotinja> findAll() {
        return zivotinjaRepository.findAll();
    }

    public Optional<Zivotinja> findById(Long id) {
        return zivotinjaRepository.findById(id);
    }

    public Zivotinja save(Zivotinja zivotinja) {
        return zivotinjaRepository.save(zivotinja);
    }

    public void delete(Zivotinja zivotinja) {
        zivotinjaRepository.delete(zivotinja);
    }
    public BigDecimal ukupniTroskoviZaZivotinju(Long zivotinjaId) {
        Optional<Zivotinja> zivotinja = zivotinjaRepository.findById(zivotinjaId);
        return zivotinja.map(z -> z.getTroskovi().stream()
                .map(t -> t.getIznos())
                .reduce(BigDecimal.ZERO, BigDecimal::add)).orElse(BigDecimal.ZERO);
    }
}
