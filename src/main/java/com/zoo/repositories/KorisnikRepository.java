package com.zoo.repositories;

import com.zoo.models.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
    Korisnik findByKorisnickoIme(String korisnickoIme);
}
