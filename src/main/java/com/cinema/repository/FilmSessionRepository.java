package com.cinema.repository;

import com.cinema.model.FilmSession;

import java.util.Optional;

public interface FilmSessionRepository {

    FilmSession save(FilmSession filmSession);

    Optional<FilmSession> findById(int id);

    boolean deleteById(int id);

}
