package com.zoo.repositories;

import com.zoo.models.Obaveza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObavezaRepository extends JpaRepository<Obaveza, Long> {
    List<Obaveza> findByRadnoVrijemeId(Long radnoVrijemeId);

}