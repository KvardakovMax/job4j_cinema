package com.cinema.repository;

import com.cinema.model.File;

import java.util.Optional;

public interface FileRepository {

    File save(File file);

    Optional<File> findById(int id);

    boolean deleteById(int id);

}
