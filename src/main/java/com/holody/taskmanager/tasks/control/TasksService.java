package com.holody.taskmanager.tasks.control;

import com.holody.taskmanager.Clock;
import com.holody.taskmanager.tasks.boundary.TaskRepository;
import com.holody.taskmanager.tasks.entity.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TasksService {

    private final TaskRepository taskRepository;
    private final AtomicLong nexTaskId = new AtomicLong(0L);
    private final Clock clock;

    public TasksService(TaskRepository taskRepository, Clock clock) {
        this.taskRepository = taskRepository;
        this.clock = clock;
    }

    public void addTask(String title, String author, String description) {
        taskRepository.add(
                new Task(
                        nexTaskId.getAndIncrement(),
                        title,
                        author,
                        description,
                        clock.time()
                ));
    }


    public Task fetchById(Long id) {
        return taskRepository.fetchById(id);
    }

    public void updateTask(Long id, String title, String author, String description) {

        taskRepository.update(id, title, author, description);
    }

    public List<Task> fetchAll() {
        return taskRepository.fetchAll();
    }

    public List<Task> filterAllByQuery(String query) {
        return taskRepository.fetchAll()
                .stream()
                .filter(task -> task.getTitle().contains(query) ||
                        task.getAuthor().contains(query) ||
                        task.getDescription().contains(query))
                .collect(toList());
    }


}
