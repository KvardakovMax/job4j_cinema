package com.cinema.service;

import com.cinema.model.File;

import java.util.Optional;

public interface FileService {

    Optional<File> save(File file);

    Optional<File> findById(int id);

    boolean deleteById(int id);

}
