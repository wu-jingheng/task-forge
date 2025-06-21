package com.moxie.task_forge.dto;

public record TaskDTO(
        String id,
        String title,
        String description,
        String assigneeId
) {
}
