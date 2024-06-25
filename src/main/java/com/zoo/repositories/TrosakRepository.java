package com.zoo.repositories;

import com.zoo.models.Trosak;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrosakRepository extends JpaRepository<Trosak, Long> {
    List<Trosak> findByZivotinjaId(Long zivotinjaId);
}