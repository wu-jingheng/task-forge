package com.moxie.task_forge.service;

import com.moxie.task_forge.dto.UserCreateDTO;
import com.moxie.task_forge.dto.UserDTO;
import com.moxie.task_forge.exception.UserNotFoundException;
import com.moxie.task_forge.mapper.UserMapper;
import com.moxie.task_forge.model.User;
import com.moxie.task_forge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository repository;
    @Mock private UserMapper mapper;
    @Mock private PasswordEncoder encoder;

    @InjectMocks private UserService service;

    @Test
    void createUser_shouldWork() {
        UserCreateDTO createDTO = new UserCreateDTO("alice", "password", List.of("USER"));
        User userEntity = new User("1", "alice", "hashed", List.of("USER"));
        User savedUser = new User("1","alice", "hashed", List.of("USER"));
        savedUser.setId("1");
        UserDTO returnedDto = new UserDTO("1", "alice", List.of("USER"));

        when(mapper.toEntity(createDTO)).thenReturn(userEntity);
        when(encoder.encode("password")).thenReturn("hashed");
        when(repository.save(userEntity)).thenReturn(savedUser);
        when(mapper.toDto(savedUser)).thenReturn(returnedDto);

        UserDTO dto = service.createUser(createDTO);

        assertNotNull(dto);
        assertEquals("1", dto.id());
        assertEquals("alice", dto.username());
    }

    @Test
    void getUser_byUsername_shouldWork() {
        User user = new User("1","alice", "hashed", List.of("USER"));
        user.setId("1");
        UserDTO dto = new UserDTO("1", "alice", List.of("USER"));

        when(repository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(dto);

        UserDTO result = service.getUser("alice");
        assertEquals("alice", result.username());
    }

    @Test
    void findByUsername_shouldThrowIfNotFound() {
        when(repository.findByUsername("bob")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getUser("bob"));
    }

    @Test
    void deleteByUsername_shouldWork() {
        User user = new User("1","alice", "hashed", List.of("USER"));
        when(repository.findByUsername("alice")).thenReturn(Optional.of(user));

        service.deleteUser("alice");

        verify(repository).delete(user);
    }

    @Test
    void deleteByUsername_shouldThrowIfNotFound() {
        when(repository.findByUsername("bob")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.deleteUser("bob"));
    }

    @Test
    void getAllUsers_shouldWork() {
        Pageable pageable = PageRequest.of(0, 10);
        User user = new User("1","alice", "hashed", List.of("USER"));
        user.setId("1");
        UserDTO dto = new UserDTO("1", "alice", List.of("USER"));

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(user)));
        when(mapper.toDto(user)).thenReturn(dto);

        Page<UserDTO> result = service.getAllUsers(pageable);
        assertEquals(1L, result.getTotalElements());
        assertEquals("alice", result.stream().toList().getFirst().username());
    }
}