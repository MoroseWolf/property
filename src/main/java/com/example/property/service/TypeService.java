package com.example.property.service;

import com.example.property.exception.exceptions.TypeNotFoundException;
import com.example.property.model.Type;
import com.example.property.service.dto.TypeDTO;
import com.example.property.service.mapper.TypeMapper;
import com.example.property.service.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public List<TypeDTO> getTypeList(Authentication authentication) {
        return typeMapper.toTypeDTOs(typeRepository.findAll());
    }

    public TypeDTO getTypeById(Long id, Authentication authentication) {
        Type type = typeRepository.findById(id).orElseThrow(TypeNotFoundException::new);
        return typeMapper.toTypeDTO(type);
    }

    public TypeDTO saveType(Long id, TypeDTO typeDTO, Authentication authentication) {
        Type type = typeMapper.toType(typeDTO);
        if (id != null) type.setId(id);
        return typeMapper.toTypeDTO(typeRepository.save(type));
    }

    public void deleteType(Long typeId, Authentication authentication) {
        typeRepository.deleteById(typeId);
    }
}
