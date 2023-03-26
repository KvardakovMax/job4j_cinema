package com.cinema.service;

import com.cinema.model.File;
import com.cinema.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;

    public SimpleFileService(FileRepository sql2oFileRepository) {
        this.fileRepository = sql2oFileRepository;
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public Optional<File> findById(int id) {
        return fileRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return fileRepository.deleteById(id);
    }
}
