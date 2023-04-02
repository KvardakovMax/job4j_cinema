package com.cinema.service;

import com.cinema.model.Hall;
import com.cinema.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleHallService implements HallService {

    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository sql2oHallRepository) {
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Optional<Hall> save(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }
}
