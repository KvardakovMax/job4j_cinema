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

class Sql2oTicketRepositoryTest {

    static Sql2oTicketRepository sql2oTicketRepository;
    static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    static Sql2oUserRepository sql2oUserRepository;
    static Sql2oFilmRepository sql2oFilmRepository;
    static Sql2oHallRepository sql2oHallRepository;
    static Sql2oFileRepository sql2oFileRepository;
    static Sql2oGenreRepository sql2oGenreRepository;
    private static User user;
    private static FilmSession filmSession;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepository.class.getClassLoader().
                getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
        sql2oFileRepository = new Sql2oFileRepository(sql2o);
        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);

        try {
            var file = sql2oFileRepository.save(new File("test", "test"));
            var genre = sql2oGenreRepository.save(new Genre("fantasy"));
            var film = sql2oFilmRepository.save(new Film("test", "test", 15, genre.getId(), 15, 15, file.get().getId())).get();
            var hall = sql2oHallRepository.save(new Hall("test hall", 1, 4, "test"));

            filmSession = sql2oFilmSessionRepository.save(new FilmSession(film.getId(), hall.get().getId(),
                    LocalDateTime.of(2023, 4, 2, 2, 10),
                    LocalDateTime.of(2023, 4, 2, 3, 10),
                    100));
            user = sql2oUserRepository.save(new User("testName", "testEmail", "testPassword")).get();
        } catch (Sql2oException e) {
            e.getMessage();
            clearRepositories();
        }
    }

    @AfterAll
    public static void clearRepositories() {
        var users = sql2oUserRepository.findAll();
        for (User user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }

        var sessions = sql2oFilmSessionRepository.findAll();
        for (FilmSession session : sessions) {
            sql2oFilmSessionRepository.deleteById(session.getId());
        }

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
    public void cleanTickets() {
        var tickets = sql2oTicketRepository.findAll();
        for (Ticket ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var ticket = sql2oTicketRepository.save(new Ticket(filmSession.getId(), 1, 4, user.getId())).get();
        var savedTicket = sql2oTicketRepository.findById(ticket.getId()).get();
        assertThat(ticket.getId()).isEqualTo(savedTicket.getId());
    }

    @Test
    public void whenSaveTheSameThenGetOptionalEmpty() {
        var ticket = sql2oTicketRepository.save(new Ticket(filmSession.getId(), 1, 4, user.getId())).get();
        var sameTicket = sql2oTicketRepository.save(ticket);
        assertThat(sameTicket).isEmpty();
    }

    @Test
    public void whenSaveThenDeleteGetTrue() {
        var ticket = sql2oTicketRepository.save(new Ticket(filmSession.getId(), 1, 4, user.getId())).get();
        var deleted = sql2oTicketRepository.deleteById(ticket.getId());
        assertThat(deleted).isTrue();
    }

    @Test
    public void whenDeletedNonExistedTicketGetFalse() {
        var deleted = sql2oTicketRepository.deleteById(0);
        assertThat(deleted).isFalse();
    }

}