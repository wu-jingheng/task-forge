package com.moxie.task_forge.service;

import com.moxie.task_forge.exception.TaskNotFoundException;
import com.moxie.task_forge.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void addAndGetTask_shouldWork() {
        TaskService service = new TaskService();
        Task task = new Task("1", "Test task", "Test description");

        service.addTask(task);

        Task retrievedTask = service.getTask("1");
        assertEquals("Test task", retrievedTask.getTitle());
    }

    @Test
    void updateAndGetTask_shouldWork() {
        TaskService service = new TaskService();
        Task task = new Task("1", "Test task", "Test description");
        service.addTask(task);
        task.setTitle("Updated task");

        service.updateTask(task);

        Task retrievedTask = service.getTask("1");
        assertEquals("Updated task", retrievedTask.getTitle());
    }

    @Test
    void updateAndGetTask_shouldThrow() {
        TaskService service = new TaskService();
        Task task = new Task("1", "Test task", "Test description");

        assertThrows(TaskNotFoundException.class, () -> service.updateTask(task));
    }

    @Test
    void deleteTask_shouldWork() {
        TaskService service = new TaskService();
        Task task = new Task("1", "Test task", "Test description");
        service.addTask(task);

        assertDoesNotThrow(() -> service.removeTask("1"));
    }

    @Test
    void deleteTask_shouldThrow() {
        TaskService service = new TaskService();

        assertThrows(TaskNotFoundException.class, () -> service.removeTask("1"));
    }

    @Test
    void listTasks_shouldWork() {
        TaskService service = new TaskService();
        Task task1 = new Task("1", "Test task", "Test description");
        Task task2 = new Task("2", "Test task", "Test description");
        Task task3 = new Task("3", "Test task", "Test description");
        service.addTask(task1);
        service.addTask(task2);
        service.addTask(task3);

        List<Task> retrievedTasks = service.listTasks();

        assertEquals(3, retrievedTasks.size());
    }
}