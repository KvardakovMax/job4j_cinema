package com.cinema.repository;

import com.cinema.model.FilmSession;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO film_sessions(id, film_id, halls_id, start_time, end_time, price)
                    VALUES(:id, filmId, :hallsId, :startTime, :endTime, :price)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("id", filmSession.getId())
                    .addParameter("filmId", filmSession.getFilmId())
                    .addParameter("hallsId", filmSession.getHallsId())
                    .addParameter("startTime", filmSession.getStartTime())
                    .addParameter("endTime", filmSession.getEndTime())
                    .addParameter("price", filmSession.getPrice());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            filmSession.setId(generatedId);
            return filmSession;
        }
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM film_sessions WHERE id = :id")
                    .addParameter("id", id);
            FilmSession filmSession = query.executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM film_sessions WHERE id = :id")
                    .addParameter("id", id);
            int effectedRow = query.executeUpdate().getResult();
            return effectedRow > 0;
        }
    }
}
