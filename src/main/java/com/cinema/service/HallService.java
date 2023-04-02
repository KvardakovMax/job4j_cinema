package com.cinema.service;

import com.cinema.model.Hall;

import java.util.Optional;

public interface HallService {

    Optional<Hall> save(Hall hall);

    Optional<Hall> findById(int id);

}
