package com.cinema.repository;

import com.cinema.configuration.DatasourceConfiguration;
import com.cinema.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2oException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFilmSessionRepositoryTest {

    static Sql2oFileRepository sql2oFileRepository;
    static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    static Sql2oHallRepository sql2oHallRepository;
    static Sql2oFilmRepository sql2oFilmRepository;
    static Sql2oGenreRepository sql2oGenreRepository;
    static Film film;
    static Hall hall;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepository.class.getClassLoader().
                getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);

        try {
            var file = sql2oFileRepository.save(new File("test", "test"));
            var genre = sql2oGenreRepository.save(new Genre("fantasy"));
            film = sql2oFilmRepository.save(new Film("test", "test", 15, genre.getId(), 15, 15, file.get().getId())).get();
            hall = sql2oHallRepository.save(new Hall("test hall", 1, 4, "test")).get();
        } catch (Sql2oException e) {
            e.getMessage();
            cleanRepositories();
        }
    }

    @AfterAll
    public static void cleanRepositories() {

        var halls = sql2oHallRepository.findAll();
        for (Hall hall : halls) {
            sql2oHallRepository.deleteById(hall.getId());
        }

        var films = sql2oFilmRepository.findAll();
        for (Film film : films) {
            sql2oFilmRepository.deleteById(film.getId());
        }

        var files = sql2oFileRepository.findAll();
        for (File file : files) {
            sql2oFileRepository.deleteById(file.getId());
        }

        var genres = sql2oGenreRepository.findAll();
        for (Genre genre : genres) {
            sql2oGenreRepository.deleteById(genre.getId());
        }
    }

    @AfterEach
    public void clearFilmSessions() {
        var sessions = sql2oFilmSessionRepository.findAll();
        for (FilmSession session : sessions) {
            sql2oFilmSessionRepository.deleteById(session.getId());
        }
    }

    @Test
    public void whenSaveThenDeleteGetTrue() {
        var filmSession = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.getId(),
                LocalDateTime.of(2023, 4, 2, 2, 10),
                LocalDateTime.of(2023, 4, 2, 3, 10), 100));
        assertThat(filmSession).isNotNull();
        var deleted = sql2oFilmSessionRepository.deleteById(filmSession.getId());
        assertThat(deleted).isTrue();
    }

}