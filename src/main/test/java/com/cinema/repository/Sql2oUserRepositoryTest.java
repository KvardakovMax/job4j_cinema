package com.cinema.repository;

import com.cinema.configuration.DatasourceConfiguration;
import com.cinema.model.User;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class Sql2oUserRepositoryTest {
    static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (User user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User(0, "name@gmail.com", "Max", "qwerty")).get();
        var savedUser = sql2oUserRepository.findByEmail(user.getEmail());
        AssertionsForClassTypes.assertThat(user).usingRecursiveComparison().isEqualTo(savedUser.get());
    }

}