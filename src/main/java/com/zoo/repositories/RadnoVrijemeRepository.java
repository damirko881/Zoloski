package com.zoo.repositories;

import com.zoo.models.RadnoVrijeme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RadnoVrijemeRepository extends JpaRepository<RadnoVrijeme, Long> {
    List<RadnoVrijeme> findByRadnikIdAndDatum(Long radnikId, LocalDate Date);

}