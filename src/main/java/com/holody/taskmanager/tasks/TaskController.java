package com.holody.taskmanager.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/")
public class TaskController {

    private final TaskRepository taskRepository;


    public TaskController(TaskRepository taskRepository) {

        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getTasks() {
        log.info("Fetching all tasks ...");
        return taskRepository.fetchAll();
    }


    @PostMapping
    public void addTask(@RequestBody Task task) {
        log.info("Storing new task: {}",task);
        taskRepository.add(task);
    }
}
