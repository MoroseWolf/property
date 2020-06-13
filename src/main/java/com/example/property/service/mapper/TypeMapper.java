package com.example.property.service.mapper;

import com.example.property.model.Type;
import com.example.property.service.dto.TypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    TypeDTO toTypeDTO(Type type);

    List<TypeDTO> toTypeDTOs(List<Type> brands);

    Type toType(TypeDTO typeDTO);
}
