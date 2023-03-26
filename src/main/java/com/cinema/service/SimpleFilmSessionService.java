package com.cinema.service;

import com.cinema.model.FilmSession;
import com.cinema.repository.FilmSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmeSessionRepository) {
        this.filmSessionRepository = sql2oFilmeSessionRepository;
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
    public boolean deleteById(int id) {
        return filmSessionRepository.deleteById(id);
    }
}
