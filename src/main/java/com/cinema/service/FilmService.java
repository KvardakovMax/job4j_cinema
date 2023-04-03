package com.cinema.service;

import com.cinema.dto.FilmDto;
import com.cinema.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmService {

    Optional<Film> save(Film film);

    Optional<Film> findById(int id);

    boolean deleteById(int id);

    Collection<Film> findAll();

    Collection<FilmDto> findAllDto();

}
