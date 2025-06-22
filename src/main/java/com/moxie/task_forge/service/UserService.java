package com.moxie.task_forge.service;

import com.moxie.task_forge.dto.UserCreateDTO;
import com.moxie.task_forge.dto.UserDTO;
import com.moxie.task_forge.dto.UserUpdateDTO;
import com.moxie.task_forge.exception.UserNotFoundException;
import com.moxie.task_forge.mapper.UserMapper;
import com.moxie.task_forge.model.User;
import com.moxie.task_forge.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO createUser(UserCreateDTO createDTO) {
        User user = userMapper.toEntity(createDTO);
        user.setPassword(passwordEncoder.encode(createDTO.password()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDTO updateUser(String username, UserUpdateDTO updateDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        String newEncryptedPassword = passwordEncoder.encode(updateDTO.password());
        user.updateUser(newEncryptedPassword, updateDTO.roles());
        return userMapper.toDto(user);
    }

    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.toDto(user);
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        userRepository.delete(user);
    }
}
