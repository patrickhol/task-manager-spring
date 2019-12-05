package com.holody.taskmanager.tasks.boundary;

import com.holody.taskmanager.exceptions.NotFoundException;
import com.holody.taskmanager.tasks.entity.Task;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemoryTaskRepository implements TaskRepository {

    private final Set<Task> tasks = new HashSet<>();

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public List<Task> fetchAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public Task fetchById(Long id) {

        return findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found! "));
    }

    @Override
    public void deleteById(Long id) {
        findById(id)
                .ifPresent(tasks::remove);
    }

    @Override
    public void update(Long id, String title, String author, String description) {
        Task task = findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found! "));
        task.setTitle(title);
        task.setAuthor(author);
        task.setDescription(description);

    }

    private Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(task -> id.equals(task.getId()))
                .findFirst();
    }

}
