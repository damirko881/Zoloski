package com.zoo.service;

import com.zoo.models.Korisnik;
import com.zoo.repositories.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KorisnikDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    KorisnikRepository repository;

    @Override
    public com.zoo.models.KorisnikDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik u=repository.findByKorisnickoIme(username);
        return new com.zoo.models.KorisnikDetails(u);
    }
}