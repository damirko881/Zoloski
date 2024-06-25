package com.zoo.repositories;

import com.zoo.models.Osposobljavanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OsposobljavanjeRepository extends JpaRepository<Osposobljavanje, Long> {
}