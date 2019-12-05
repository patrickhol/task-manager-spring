package com.holody.taskmanager.tasks.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;


@Data
@AllArgsConstructor
public class Task {

    private long id;
    private String title;
    private String author;
    private String description;
    private LocalDateTime createdAt;
    private HashSet<String> files;

}
