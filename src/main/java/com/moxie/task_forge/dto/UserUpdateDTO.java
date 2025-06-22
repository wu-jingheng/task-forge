package com.moxie.task_forge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserUpdateDTO(
        @NotBlank @Size(min = 10, max = 20)
        String password,

        List<@NotBlank String> roles
) {
}
