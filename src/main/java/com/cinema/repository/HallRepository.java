package com.cinema.repository;

import com.cinema.model.Hall;

import java.util.Collection;
import java.util.Optional;

public interface HallRepository {

    Hall save(Hall hall);

    Optional<Hall> findById(int id);

    Collection<Hall> findAll();

    boolean deleteById(int id);

}
