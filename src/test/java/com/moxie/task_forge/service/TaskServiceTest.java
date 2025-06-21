package com.moxie.task_forge.service;

import com.moxie.task_forge.dto.TaskCreateDTO;
import com.moxie.task_forge.dto.TaskDTO;
import com.moxie.task_forge.dto.TaskUpdateDTO;
import com.moxie.task_forge.exception.TaskNotFoundException;
import com.moxie.task_forge.mapper.TaskMapper;
import com.moxie.task_forge.model.Task;
import com.moxie.task_forge.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskService service;

    @Test
    void createTask_shouldWork() {
        // Arrange
        TaskCreateDTO createDTO = new TaskCreateDTO("Title", "Description", "1");
        Task taskEntity = new Task("1", "Title", "Description", "1");
        Task savedTask = new Task("1", "Title", "Description", "1");
        TaskDTO returnedDto = new TaskDTO("1", "Title", "Description", "1");

        // Stub mapper.toEntity to return a Task entity when called with createDTO
        when(mapper.toEntity(createDTO)).thenReturn(taskEntity);

        // Stub repository.save to return a Task with generated ID
        when(repository.save(taskEntity)).thenReturn(savedTask);

        // Stub mapper.toDto to return a TaskDTO when called with savedTask
        when(mapper.toDto(savedTask)).thenReturn(returnedDto);

        // Act
        TaskDTO dto = service.createTask(createDTO);

        // Assert
        assertNotNull(dto);
        assertEquals("1", dto.id());
        assertEquals("Title", dto.title());
        assertEquals("Description", dto.description());
        assertEquals("1", dto.assigneeId());
    }

    @Test
    void getTaskById_shouldWork() {
        Task task = new Task("1", "Title", "Description", "1");
        TaskDTO dto = new TaskDTO("1", "Title", "Description", "1");
        when(repository.findById("1")).thenReturn(Optional.of(task));
        when(mapper.toDto(task)).thenReturn(dto);

        TaskDTO result = service.getTaskById("1");
        assertEquals("1", result.id());
        assertEquals("Title", result.title());
    }

    @Test
    void getTaskById_shouldThrowIfNotFound() {
        when(repository.findById("notFound")).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> service.getTaskById("notFound"));
    }

    @Test
    void updateTask_shouldWork() {
        TaskUpdateDTO updateDTO = new TaskUpdateDTO("1", "Updated", "Updated desc", "2");
        Task task = new Task("1", "Old", "Old desc", "1");
        when(repository.findById("1")).thenReturn(Optional.of(task));

        // No exception thrown means success
        service.updateTask(updateDTO);

        verify(repository).save(any(Task.class));
        assertEquals("Updated", task.getTitle());
        assertEquals("Updated desc", task.getDescription());
        assertEquals("2", task.getAssigneeId());
    }

    @Test
    void updateTask_shouldThrowIfNotFound() {
        TaskUpdateDTO updateDTO = new TaskUpdateDTO("notFound", "Title", "Desc", "1");
        when(repository.findById("notFound")).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> service.updateTask(updateDTO));
    }

    @Test
    void getAllTasks_shouldWork() {
        Pageable pageable = PageRequest.of(0, 10);
        Task task = new Task("1", "Title", "Description", "1");
        TaskDTO dto = new TaskDTO("1", "Title", "Description", "1");
        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(task)));
        when(mapper.toDto(task)).thenReturn(dto);

        List<TaskDTO> result = service.getAllTasks(pageable);
        assertEquals(1, result.size());
        assertEquals("1", result.getFirst().id());
    }
}