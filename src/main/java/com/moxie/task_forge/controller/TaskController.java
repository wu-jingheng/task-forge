package com.moxie.task_forge.controller;

import com.moxie.task_forge.common.ApiResponse;
import com.moxie.task_forge.dto.TaskCreateDTO;
import com.moxie.task_forge.dto.TaskDTO;
import com.moxie.task_forge.dto.TaskUpdateDTO;
import com.moxie.task_forge.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskDTO>> createTask(@Valid TaskCreateDTO createDTO) {
        TaskDTO dto = taskService.createTask(createDTO);
        ApiResponse<TaskDTO> response = new ApiResponse<>(dto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTask(@PathVariable String id) {
        TaskDTO dto = taskService.getTaskById(id);
        ApiResponse<TaskDTO> response = new ApiResponse<>(dto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(@PathVariable String id,
                                                           @Valid TaskUpdateDTO updateDTO) {
        taskService.updateTask(id, updateDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TaskDTO>>> getAllTasks(@RequestParam int pageNumber,
                                                                  @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<TaskDTO> page = taskService.getAllTasks(pageable);
        ApiResponse<Page<TaskDTO>> response = new ApiResponse<>(page);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
