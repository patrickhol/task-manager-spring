package com.holody.taskmanager.tasks.boundary;

import lombok.Data;

@Data
class UpdateTaskRequest {
    String title;
    String author;
    String description;
}
