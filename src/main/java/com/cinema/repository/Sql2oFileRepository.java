package com.cinema.repository;

import com.cinema.model.File;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFileRepository implements FileRepository {

    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<File> save(File file) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO files(name, path)
                    VALUES(:name, :path)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            file.setId(generatedId);
            return Optional.ofNullable(file);
        } catch (Sql2oException e) {
            e.getMessage();
            return Optional.empty();
        }
    }

    @Override
    public Optional<File> findById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT  * FROM files WHERE id = :id")
                    .addParameter("id", id);
            File file = query.executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }

    @Override
    public Collection<File> findAll() {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM files");
            return query.executeAndFetch(File.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM files WHERE id = :id")
                    .addParameter("id", id);
            int effectedRow = query.executeUpdate().getResult();
            return effectedRow > 0;
        }
    }
}
