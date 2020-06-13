package com.example.property.page.request;

import com.example.property.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemPageRequestMapper {
    @Mapping(source = "page", target = "pageNumber")
    @Mapping(source = "size", target = "pageSize")
    @Mapping(source = "sort", target = "sortByKey")
    @Mapping(source = "filter", target = "filterString")
    ItemPageRequest toItemPageRequest(ItemPageRequestDTO itemPageRequestDTO);

    ItemPageRequestDTO toItemPageRequestDTO(ItemPageRequest itemPageRequest);

    List<Item> toItemPageRequestDTOs(List<Item> itemPageRequests);
}
