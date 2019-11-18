package com.holody.taskmanager.tasks;

import java.util.List;

public interface TaskRepository {

    void add(Task task);

    List<Task> fetchAll();
}
