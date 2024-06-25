package com.zoo.repositories;

import com.zoo.models.Zivotinja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZivotinjaRepository extends JpaRepository<Zivotinja, Long> {
}