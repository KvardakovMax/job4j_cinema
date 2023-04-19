package com.cinema.service;

import com.cinema.model.FilmSession;
import com.cinema.repository.FilmSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        return filmSessionRepository.save(filmSession);
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        return filmSessionRepository.findById(id);
    }

    @Override
    public Collection<FilmSession> findAll() {
        return filmSessionRepository.findAll();
    }

    @Override
    public boolean deleteById(int id) {
        return filmSessionRepository.deleteById(id);
    }
}
