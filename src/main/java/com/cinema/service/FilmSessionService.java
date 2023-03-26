package com.cinema.service;

import com.cinema.model.FilmSession;

import java.util.Optional;

public interface FilmSessionService {

    FilmSession save(FilmSession filmSession);

    Optional<FilmSession> findById(int id);

    boolean deleteById(int id);

}
