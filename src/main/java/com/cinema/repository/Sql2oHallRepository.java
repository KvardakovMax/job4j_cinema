package com.cinema.repository;

import com.cinema.model.Hall;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Optional;

@Repository
public class Sql2oHallRepository implements HallRepository {

    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Hall save(Hall hall) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO halls(id, name, row_count, place_count, description)" +
                            "VALUES (:id, :name, :rowCount, :description)")
                    .addParameter("id", hall.getId())
                    .addParameter("name", hall.getName())
                    .addParameter("rowCount", hall.getRowCount())
                    .addParameter("placeCount", hall.getPlaceCount())
                    .addParameter("description", hall.getDescription());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            hall.setId(generatedId);
            return hall;
        }
    }

    @Override
    public Optional<Hall> findById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM halls WHERE id = :id")
                    .addParameter("id", id);
            Hall hall = query.executeAndFetchFirst(Hall.class);
            return Optional.ofNullable(hall);
        }
    }
}
