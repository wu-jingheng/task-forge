package com.moxie.task_forge.dto;

import java.util.List;

public record UserDTO(
        String id,
        String username,
        List<String> roles
) {
}
