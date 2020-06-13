package com.example.property.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    private Long id;
    private Long ownerId;
    private Long typeId;
    private String name;
    private Float size;
    private Float cost;
    private byte[] image;
    private String dislocation;
    private String description;

}
