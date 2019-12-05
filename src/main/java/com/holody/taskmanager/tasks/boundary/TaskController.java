package com.holody.taskmanager.tasks.boundary;

import com.holody.taskmanager.exceptions.NotFoundException;
import com.holody.taskmanager.tasks.control.TasksService;
import com.holody.taskmanager.tasks.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Slf4j
@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final StorageService storageService;
    private final TasksService tasksService;
    private final TaskRepository taskRepository;


    @PostConstruct
    void init() {
        tasksService.addTask("Zadanie moduł 3", "Patryk Hołody", "Opis 1");
        tasksService.addTask("Zadanie moduł 3", "Patryk Hołody", "Opis 2");
        tasksService.addTask("Zadanie moduł 3", "Patryk Hołody", "Opis 3");
    }

    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam(required = false) Optional<String> query) {
        log.info("Fetching all tasks with filter: {}", query);
        return query.map(tasksService::filterAllByQuery)
                .orElseGet(tasksService::fetchAll)
                .stream()
                .map(this::toTaskResponse)
                .collect(toList());
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity getTaskById(HttpServletResponse response, @PathVariable Long id) {
        try {
            log.info("Fetching task with id: {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(toTaskResponse(tasksService.fetchById(id)));
        } catch (NotFoundException e) {
            log.error("No task found", e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}/attachments/{filename}")
    public ResponseEntity getAttachment(@PathVariable Long id, @PathVariable String filename, HttpServletRequest request) throws IOException {
        Task task = tasksService.fetchById(id);
        if (task instanceof Task) {
            Resource resource = storageService.loadFile(filename);
            String mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(path = "/{id}/attachments")
    public ResponseEntity addAttachment(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Task task = tasksService.fetchById(id);
        if (task instanceof Task) {

            log.info(" Zapis " + file.getOriginalFilename());
            storageService.saveFile(id, file);
            tasksService.updateTask(id, task.getTitle(), task.getAuthor(), task.getDescription(), file.getOriginalFilename());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody CreateTaskRequest task) {
        try {
            log.info("Adding new task: {}", task);
            tasksService.addTask(task.title, task.author, task.description);
            return ResponseEntity.status(HttpStatus.CREATED).body(task.title + task.author + task.description + task.file);

        } catch (NotFoundException e) {
            log.error("Failed to add task ", e);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        try {
            taskRepository.deleteById(id);
            log.info("Delete a task." + id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Failed to delete task ", e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateTask(
            HttpServletResponse response,
            @PathVariable Long id, @RequestBody UpdateTaskRequest request) {
        try {
            tasksService.updateTask(id, request.title, request.author, request.description, request.file);
            log.info("Update a task.");
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            log.error("Failed to update task ", e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }


    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getAuthor(),
                task.getCreatedAt(),
                task.getFiles()
        );
    }
}
