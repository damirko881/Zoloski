package com.zoo.service;

import com.zoo.models.Incident;
import com.zoo.repositories.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    @Autowired
    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public List<Incident> findAll() {
        return incidentRepository.findAll();
    }

    public Optional<Incident> findById(Long id) {
        return incidentRepository.findById(id);
    }
    public Incident save(Incident incident) {
        return incidentRepository.save(incident);
    }

    public void deleteById(Long id) {
        incidentRepository.deleteById(id);
    }
}