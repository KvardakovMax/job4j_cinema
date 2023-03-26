package com.cinema.service;

import com.cinema.model.User;

import java.util.Optional;


public interface UserService {

    Optional<User> save(User user);

    Optional<User> findById(int id);

}
