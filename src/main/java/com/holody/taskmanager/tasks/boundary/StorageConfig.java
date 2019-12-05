package com.holody.taskmanager.tasks.boundary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.storage")
public class StorageConfig {

    private String pathToUploadFiles;

    public String getPathToUploadFiles() {
        return pathToUploadFiles;
    }

    public void setPathToUploadFiles(String pathToUploadFiles) {
        this.pathToUploadFiles = pathToUploadFiles;
    }
}
