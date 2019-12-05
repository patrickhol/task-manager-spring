package com.holody.taskmanager.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
class TaskResponse {
    long id;
    String title;
    String description;
    String author;
    LocalDateTime createdAt;
}
