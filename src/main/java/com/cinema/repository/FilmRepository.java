package com.cinema.repository;

import com.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmRepository {

    Film save(Film film);

    Optional<Film> findById(int id);

    boolean deleteById(int id);

    Collection<Film> findAll();

}
