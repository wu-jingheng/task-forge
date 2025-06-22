package com.moxie.task_forge.service;

import com.moxie.task_forge.dto.TaskCreateDTO;
import com.moxie.task_forge.dto.TaskDTO;
import com.moxie.task_forge.dto.TaskUpdateDTO;
import com.moxie.task_forge.exception.TaskNotFoundException;
import com.moxie.task_forge.mapper.TaskMapper;
import com.moxie.task_forge.model.Task;
import com.moxie.task_forge.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;
    private final Map<String, Task> tasks = new HashMap<>();

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskDTO createTask(@Valid TaskCreateDTO createDTO) {
        Task task = taskMapper.toEntity(createDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    public TaskDTO getTaskById(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toDto(task);
    }

    public void updateTask(String id, TaskUpdateDTO updateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.update(updateDTO.title(),
                updateDTO.description(),
                updateDTO.assigneeId(),
                "System Temp",
                LocalDateTime.now());

        taskRepository.save(task);
    }

    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setDeleted(true);
    }

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toDto);
    }
}
