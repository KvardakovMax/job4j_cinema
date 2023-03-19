package com.cinema.repository;

import com.cinema.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    boolean deleteById(int id);

    Collection<User> findAll();

}
