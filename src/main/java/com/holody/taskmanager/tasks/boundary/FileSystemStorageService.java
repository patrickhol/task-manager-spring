package com.holody.taskmanager.tasks.boundary;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class FileSystemStorageService implements StorageService {

    private final Path path;

    public FileSystemStorageService(Path path) {
        this.path = path;
    }

    @Override
    public void saveFile(Long taskId, MultipartFile file) throws IOException {
        Path targetPath = path.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public Resource loadFile(String filename) throws MalformedURLException {
        return new UrlResource(path.resolve(filename).toUri());
    }
}
