package com.cinema.repository;

import com.cinema.model.Film;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Film> save(Film film) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO films(name, description, year, genre_id, minimal_age, duration_in_minutes, file_id)
                    VALUES(:name, :description, :year, :genreId, :minimalAge, :durationInMinutes, :fileId)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            film.setId(generatedId);
            return Optional.ofNullable(film);
        } catch (Sql2oException e) {
            e.getMessage();
            return Optional.empty();
        }

    }

    @Override
    public Optional<Film> findById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films WHERE id = :id")
                    .addParameter("id", id);
            Film film = query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetchFirst(Film.class);
            return Optional.ofNullable(film);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM films WHERE id = :id")
                    .addParameter("id", id);
            int effectedRow = query.executeUpdate().getResult();
            return effectedRow > 0;
        }
    }

    @Override
    public Collection<Film> findAll() {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM films");
            return query.setColumnMappings(Film.COLUMN_MAPPING).executeAndFetch(Film.class);
        }
    }
}
