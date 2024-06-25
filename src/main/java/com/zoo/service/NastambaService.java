package com.zoo.service;

import com.zoo.models.Nastamba;
import com.zoo.repositories.NastambaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NastambaService {

    private final NastambaRepository nastambaRepository;

    @Autowired
    public NastambaService(NastambaRepository nastambaRepository) {
        this.nastambaRepository = nastambaRepository;
    }

    public List<Nastamba> findAll() {
        return nastambaRepository.findAll();
    }

    public Optional<Nastamba> findById(Long id) {
        return nastambaRepository.findById(id);
    }

    public Nastamba save(Nastamba nastamba) {
        return nastambaRepository.save(nastamba);
    }

    public void delete(Nastamba nastamba) {
        nastambaRepository.delete(nastamba);
    }
}