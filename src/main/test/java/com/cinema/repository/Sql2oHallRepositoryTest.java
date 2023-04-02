package com.cinema.repository;

import com.cinema.configuration.DatasourceConfiguration;
import com.cinema.model.Hall;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oHallRepositoryTest {

    static Sql2oHallRepository sql2oHallRepository;

    @BeforeAll
    public static void intiRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepository.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @AfterEach
    public void cleanHalls(){
        var halls = sql2oHallRepository.findAll();
        for (Hall hall : halls) {
            sql2oHallRepository.deleteById(hall.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var hall = sql2oHallRepository.save(new Hall("testName", 2, 6, "testDescription")).get();
        var sameHall = sql2oHallRepository.findById(hall.getId()).get();
        assertThat(hall).isEqualTo(sameHall);
    }

    @Test
    public void whenSaveThenDeleteReturnEmptyOptional() {
        var hall = sql2oHallRepository.save(new Hall("testName", 2, 6, "testDescription")).get();
        var deleted = sql2oHallRepository.deleteById(hall.getId());
        var deletedHall = sql2oHallRepository.findById(hall.getId());
        assertThat(deleted).isTrue();
        assertThat(deletedHall).isEmpty();
    }

    @Test
    public void whenInvalidDeleteThenFalse() {
        var deleted = sql2oHallRepository.deleteById(0);
        assertThat(deleted).isFalse();
    }

}