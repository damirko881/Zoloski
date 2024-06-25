package com.zoo.repositories;

import com.zoo.models.Nastamba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NastambaRepository extends JpaRepository<Nastamba, Long> {
}