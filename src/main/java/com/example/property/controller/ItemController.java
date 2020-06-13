package com.example.property.controller;

import com.example.property.common.ApplicationProperties;
import com.example.property.page.ItemPageDTO;
import com.example.property.page.request.ItemPageRequestDTO;
import com.example.property.service.ItemService;
import com.example.property.service.dto.ItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(ApplicationProperties.ITEM_API_URL)
public class ItemController {

    private final ItemService itemService;

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping(value = "", produces = "application/json; charset=UTF-8")
    ItemPageDTO getItemList(ItemPageRequestDTO itemPageRequestDTO, Authentication authentication) {
        return itemService.getItemList(itemPageRequestDTO, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{id}")
    ItemDTO getItemById(@PathVariable(value = "id") Long itemId, Authentication authentication) {
        return itemService.getItemById(itemId, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PostMapping("")
    ItemDTO createCar(@Valid @RequestBody ItemDTO itemDTO, Authentication authentication) {
        return itemService.saveItem(null, itemDTO, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @PutMapping("/{id}")
    public ItemDTO updateItem(@PathVariable(value = "id") Long itemId, @Valid @RequestBody ItemDTO itemDTO, Authentication authentication) {
        return itemService.saveItem(itemId, itemDTO, authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable(value = "id") Long itemId, Authentication authentication) {
        itemService.deleteItem(itemId, authentication);
    }
}
