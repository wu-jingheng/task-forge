package com.moxie.task_forge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateDTO(
        @NotBlank @Size(min = 3, max = 100) String title,

        @NotBlank @Size(min = 3, max = 1000) String description,

        @NotBlank String assigneeId
) {
}
