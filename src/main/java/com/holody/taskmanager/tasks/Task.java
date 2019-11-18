package com.holody.taskmanager.tasks;


import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {

    private String title;
    private String author;
    private LocalDate createdAt = LocalDate.now();

}
