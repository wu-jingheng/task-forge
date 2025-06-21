package com.moxie.task_forge.mapper;

import com.moxie.task_forge.dto.TaskCreateDTO;
import com.moxie.task_forge.dto.TaskDTO;
import com.moxie.task_forge.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDto(Task task);

    @Mapping(target = "id", ignore = true)
    Task toEntity(TaskCreateDTO dto);
}
