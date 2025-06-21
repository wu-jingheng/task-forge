package com.moxie.task_forge.mapper;

import com.moxie.task_forge.dto.UserCreateDTO;
import com.moxie.task_forge.dto.UserDTO;
import com.moxie.task_forge.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateDTO dto);
}
