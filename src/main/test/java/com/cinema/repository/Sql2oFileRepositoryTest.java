package com.cinema.repository;

import com.cinema.configuration.DatasourceConfiguration;
import com.cinema.model.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFileRepositoryTest {

    static Sql2oFileRepository sql2oFileRepository;

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

        sql2oFileRepository = new Sql2oFileRepository(sql2o);
    }

    @AfterEach
    public void cleanFiles() {
        var files = sql2oFileRepository.findAll();
        for (File file : files) {
            sql2oFileRepository.deleteById(file.getId());
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var file = sql2oFileRepository.save(new File("test", "test")).get();
        var sameFile = sql2oFileRepository.findById(file.getId()).get();
        assertThat(file).isEqualTo(sameFile);
    }

    @Test
    public void whenInvalidDeleteThenGetFalse() {
        var deleted = sql2oFileRepository.deleteById(0);
        assertThat(deleted).isFalse();
    }

    @Test
    public void whenSaveThenDeleteGetEmptyOptional() {
        var file = sql2oFileRepository.save(new File("test", "test")).get();
        sql2oFileRepository.deleteById(file.getId());
        var sameFile = sql2oFileRepository.findById(file.getId());
        assertThat(sameFile).isEmpty();
    }

}