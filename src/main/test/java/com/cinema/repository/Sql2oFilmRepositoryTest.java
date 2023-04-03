package com.cinema.repository;

import com.cinema.configuration.DatasourceConfiguration;
import com.cinema.model.File;
import com.cinema.model.Film;
import com.cinema.model.Genre;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2oException;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFilmRepositoryTest {

    static Sql2oFilmRepository sql2oFilmRepository;
    static Sql2oGenreRepository sql2oGenreRepository;
    static Sql2oFileRepository sql2oFileRepository;
    static File file;
    static Genre genre;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepository.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        try {
            file = sql2oFileRepository.save(new File("test", "test")).get();
            genre = sql2oGenreRepository.save(new Genre("fantasy"));
        } catch (
                Sql2oException e) {
            e.getMessage();
            cleanRepositories();
        }
    }

    @AfterAll
    public static void cleanRepositories() {
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
    public void cleanFilms() {
        var films = sql2oFilmRepository.findAll();
        for (Film film : films) {
            sql2oFilmRepository.deleteById(film.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame(){
        var film = sql2oFilmRepository.save(new Film("testN", "testD", 1999, genre.getId(), 120, 120, file.getId()));
        var sameFilm = sql2oFilmRepository.findById(film.get().getId());
        assertThat(film).isEqualTo(sameFilm);
    }

    @Test
    public void whenSaveThenDeleteGetOptionalEmpty() {
        var film = sql2oFilmRepository.save(new Film("testN", "testD", 1999, genre.getId(), 120, 120, file.getId()));
        sql2oFilmRepository.deleteById(film.get().getId());
        var sameFilm = sql2oFilmRepository.findById(film.get().getId());
        assertThat(sameFilm).isEmpty();
    }

    @Test
    public void whenInvalidDeleteThenFalse() {
        var deleted = sql2oFilmRepository.deleteById(0);
        assertThat(deleted).isFalse();
    }

}