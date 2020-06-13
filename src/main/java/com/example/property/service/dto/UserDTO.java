package com.example.property.service.dto;

import com.example.property.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    Long id;
    Set<Role> roles = new HashSet<>();
    String username;

}