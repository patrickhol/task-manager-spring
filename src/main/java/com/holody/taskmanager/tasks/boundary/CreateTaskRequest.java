package com.holody.taskmanager.tasks.boundary;

import lombok.Data;

@Data
class CreateTaskRequest {
    String title;
    String author;
    String description;
    String file;
}
