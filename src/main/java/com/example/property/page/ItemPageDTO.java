package com.example.property.page;

import com.example.property.service.dto.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPageDTO {
    String status;
    Integer code;
    List<ItemDTO> items;
    Long totalElements;
    Integer totalPages;
}
