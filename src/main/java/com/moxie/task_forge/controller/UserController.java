package com.moxie.task_forge.controller;

import com.moxie.task_forge.common.ApiResponse;
import com.moxie.task_forge.dto.UserCreateDTO;
import com.moxie.task_forge.dto.UserDTO;
import com.moxie.task_forge.dto.UserUpdateDTO;
import com.moxie.task_forge.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid UserCreateDTO createDTO) {
        UserDTO dto = userService.createUser(createDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(dto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PutMapping("/{username}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable String username,
                                                           @Valid UserUpdateDTO updateDTO) {
        UserDTO dto = userService.updateUser(username, updateDTO);
        ApiResponse<UserDTO> response = new ApiResponse<>(dto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable String username) {
        UserDTO dto = userService.getUser(username);
        ApiResponse<UserDTO> response = new ApiResponse<>(dto);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers(@RequestParam int pageNumber,
                                                                  @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<UserDTO> page = userService.getAllUsers(pageable);
        ApiResponse<Page<UserDTO>> response = new ApiResponse<>(page);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
