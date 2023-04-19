package com.cinema.repository;

import com.cinema.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sql2oUserRepository.class);

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (Connection connection = sql2o.open()) {
            var sql = """
                    INSERT INTO users(full_name, email, password)
                    VALUES(:fullName, :email, :password)
                    """;

            var query = connection.createQuery(sql, true)
                    .addParameter("fullName", user.getFullName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            LOGGER.info("Name of the new user - {}", user.getFullName());
            int generatedId = query.setColumnMappings(User.COLUMN_MAPPING).executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.ofNullable(user);
        } catch (Sql2oException e) {
            LOGGER.error("UserRepo - {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id);
            User user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email")
                    .addParameter("email", email);
            User user = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE id = :id")
                    .addParameter("id", id);
            int affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Collection<User> findAll() {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }
}
