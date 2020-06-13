package com.example.property.service.mapper;

import com.example.property.model.User;
import com.example.property.service.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    User toUser(UserDTO userDTO);
}
