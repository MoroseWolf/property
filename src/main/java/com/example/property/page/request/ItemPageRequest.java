package com.example.property.page.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPageRequest {
    Integer pageNumber;
    Integer pageSize;
    String sortByKey;
    String filterString;
}
