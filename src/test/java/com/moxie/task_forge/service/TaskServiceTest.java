package com.moxie.task_forge.service;

import com.moxie.task_forge.dto.TaskCreateDTO;
import com.moxie.task_forge.dto.TaskDTO;
import com.moxie.task_forge.mapper.TaskMapper;
import com.moxie.task_forge.model.Task;
import com.moxie.task_forge.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
    void createTask_shouldThrow() {
        // Arrange
        TaskCreateDTO createDTO = new TaskCreateDTO(null, "Description", "1");
    }
}