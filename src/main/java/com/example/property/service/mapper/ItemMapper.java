package com.example.property.service.mapper;

import com.example.property.model.Item;
import com.example.property.service.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "owner.id", target = "ownerId")
    ItemDTO toItemDTO(Item item);

    List<ItemDTO> toItemDTOs(List<Item> items);

    Item toItem(ItemDTO itemDTO);
}
