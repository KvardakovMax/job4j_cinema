package com.cinema.repository;

import com.cinema.model.Genre;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Optional;

@Repository
public class Sql2oGenreRepository implements GenreRepository {

    private final Sql2o sql2o;

    public Sql2oGenreRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Genre save(Genre genre) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO genres(id, name) VALUES(:id, :name) ")
                    .addParameter("id", genre.getId())
                    .addParameter("name", genre.getName());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            genre.setId(generatedId);
            return genre;
        }
    }

    @Override
    public Optional<Genre> findById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM genres WHERE id = :id")
                    .addParameter("id", id);
            Genre genre = query.executeAndFetchFirst(Genre.class);
            return Optional.ofNullable(genre);
        }
    }
}
