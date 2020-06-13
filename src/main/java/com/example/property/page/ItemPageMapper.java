package com.example.property.page;

import com.example.property.model.Item;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemPageMapper {
    ItemPageDTO toItemPageDTO(ItemPage itemPage);

    List<Item> toItemPageDTOs(List<Item> itemPages);

    ItemPage toItemPage(ItemPageDTO itemPageDTO);
}
