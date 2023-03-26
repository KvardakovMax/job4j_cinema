package com.cinema.service;

import com.cinema.dto.FilmDto;
import com.cinema.model.File;
import com.cinema.model.Film;
import com.cinema.model.Genre;
import com.cinema.repository.FileRepository;
import com.cinema.repository.FilmRepository;
import com.cinema.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    private final FileRepository fileRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository,
                             FileRepository sql2oFileRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
        this.fileRepository = sql2oFileRepository;
    }

    @Override
    public Film save(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public Optional<Film> findById(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return filmRepository.deleteById(id);
    }

    @Override
    public Collection<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Collection<FilmDto> findAllDto() {
        List<FilmDto> filmsDto = new ArrayList<>();
        var films = findAll();
        for (Film film : films) {
            Optional<Genre> genre = genreRepository.findById(film.getGenreId());
            Optional<File> poster = fileRepository.findById(film.getFileId());
            if (genre.isPresent() && poster.isPresent()) {
                filmsDto.add(new FilmDto(film.getName(),
                        film.getDescription(),
                        genre.get().toString(),
                        film.getYear(),
                        film.getMinimalAge(),
                        film.getDurationInMinutes(),
                        poster.get()));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return filmsDto;
    }
}
