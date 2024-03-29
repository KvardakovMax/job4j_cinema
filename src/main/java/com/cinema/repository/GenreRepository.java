package com.cinema.repository;

import com.cinema.model.Genre;

import java.util.Collection;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(int id);

    Collection<Genre> findAll();

    boolean deleteById(int id);

}
