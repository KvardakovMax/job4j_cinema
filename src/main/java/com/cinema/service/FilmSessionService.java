package com.cinema.service;

import com.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

public interface FilmSessionService {

    FilmSession save(FilmSession filmSession);

    Optional<FilmSession> findById(int id);

    Collection<FilmSession> findAll();

    boolean deleteById(int id);

}
