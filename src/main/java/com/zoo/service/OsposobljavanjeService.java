package com.zoo.service;

import com.zoo.models.Osposobljavanje;
import com.zoo.repositories.OsposobljavanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OsposobljavanjeService {

    private final OsposobljavanjeRepository osposobljavanjeRepository;

    @Autowired
    public OsposobljavanjeService(OsposobljavanjeRepository osposobljavanjeRepository) {
        this.osposobljavanjeRepository = osposobljavanjeRepository;
    }

    public List<Osposobljavanje> findAll() {
        return osposobljavanjeRepository.findAll();
    }

    public Optional<Osposobljavanje> findById(Long id) {
        return osposobljavanjeRepository.findById(id);
    }

    public Osposobljavanje save(Osposobljavanje osposobljavanje) {
        return osposobljavanjeRepository.save(osposobljavanje);
    }

    public void delete(Osposobljavanje osposobljavanje) {
        osposobljavanjeRepository.delete(osposobljavanje);
    }
}