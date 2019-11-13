package com.holody.taskmanager.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/")
public class TasksController {

    private final TasksRepository tasksRepository;


    public TasksController(TasksRepository tasksRepository) {

        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public List<Task> getTasks() {
        log.info("Fetching all tasks ...");
        return tasksRepository.fetchAll();
    }


    @PostMapping
    public void addTask(@RequestBody Task task) {
        log.info("Storing new task: {}",task);
        tasksRepository.add(task);
    }
}
