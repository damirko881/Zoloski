package com.zoo.service;

import com.zoo.models.Trosak;
import com.zoo.repositories.TrosakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrosakService {

    private final TrosakRepository trosakRepository;

    @Autowired
    public TrosakService(TrosakRepository trosakRepository) {
        this.trosakRepository = trosakRepository;
    }

    public List<Trosak> findAll() {
        return trosakRepository.findAll();
    }

    public Trosak save(Trosak trosak) {
        return trosakRepository.save(trosak);
    }
    public Optional<Trosak> findById(Long id) {
        return trosakRepository.findById(id);
    }
    public void deleteById(Long id) {
        trosakRepository.deleteById(id);
    }
}