package com.example.property.controller;

import com.example.property.common.ApplicationProperties;
import com.example.property.service.TypeService;
import com.example.property.service.dto.TypeDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ApplicationProperties.TYPE_API_URL)
public class TypeController {

    private final TypeService typeService;

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping(value = "", produces = "application/json; charset=UTF-8")
    List<TypeDTO> getTypeList(Authentication authentication) {
        return typeService.getTypeList( authentication);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GetMapping("/{id}")
    TypeDTO getTypeById(@PathVariable(value = "id") Long typeId, Authentication authentication) {
        return typeService.getTypeById(typeId, authentication);
    }

    @RolesAllowed("ADMIN")
    @PostMapping("")
    TypeDTO createType(@Valid @RequestBody TypeDTO typeDTO, Authentication authentication) {
        return typeService.saveType(null, typeDTO, authentication);
    }

    @RolesAllowed("ADMIN")
    @PutMapping("/{id}")
    public TypeDTO updateBrand(@PathVariable(value = "id") Long typeId, @Valid @RequestBody TypeDTO typeDTO,
                                Authentication authentication) {
        return typeService.saveType(typeId, typeDTO, authentication);
    }

    @RolesAllowed("ADMIN")
    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable(value = "id") Long typeId, Authentication authentication) {
        typeService.deleteType(typeId, authentication);
    }
}
