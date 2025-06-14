package com.moxie.task_forge.service;

import com.moxie.task_forge.exception.TaskNotFoundException;
import com.moxie.task_forge.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskService {
    private final Map<String, Task> tasks = new HashMap<>();

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Task getTask(String id) {
        if (!tasks.containsKey(id)) {
            throw new TaskNotFoundException("Task not found: " + id);
        }
        return tasks.get(id);
    }

    public void removeTask(String id) {
        if (!tasks.containsKey(id)) {
            throw new TaskNotFoundException("Task removal failed, task not found: " + id);
        }
        tasks.remove(id);
    }

    public void updateTask(Task task) {
        String id = task.getId();
        if (!tasks.containsKey(id)) {
            throw new TaskNotFoundException("Task update failed, task not found: " + id);
        }
        tasks.put(id, task);
    }

    public List<Task> listTasks() {
        return tasks.values().stream()
                .toList();
    }
}
