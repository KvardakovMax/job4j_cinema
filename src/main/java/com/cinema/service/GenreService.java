package com.cinema.service;

import com.cinema.model.Genre;

import java.util.Optional;

public interface GenreService {

    Genre save(Genre genre);

    Optional<Genre> findById(int id);

}
