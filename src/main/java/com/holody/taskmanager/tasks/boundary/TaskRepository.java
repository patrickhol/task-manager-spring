package com.holody.taskmanager.tasks.boundary;

import com.holody.taskmanager.tasks.entity.Task;

import java.util.List;

public interface TaskRepository {

    void add(Task task);

    List<Task> fetchAll();

    Task fetchById(Long id);

    void deleteById(Long id);

    void update(Long id, String title, String author, String description, String file);
}
