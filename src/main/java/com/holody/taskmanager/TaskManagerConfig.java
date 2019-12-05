package com.holody.taskmanager;

import com.holody.taskmanager.tasks.boundary.FileSystemStorageService;
import com.holody.taskmanager.tasks.boundary.StorageConfig;
import com.holody.taskmanager.tasks.boundary.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@Configuration
public class TaskManagerConfig {

    private final StorageConfig config;

    @Autowired
    public TaskManagerConfig(StorageConfig config) {
        this.config = config;
    }

    @Bean
    public Clock clock() {
        return new SystemClock();

    }

    @Bean
    StorageService storageService() {
        System.out.println(config.getPathToUploadFiles());
        return new FileSystemStorageService(
                Paths.get(config.getPathToUploadFiles()));
    }
}
