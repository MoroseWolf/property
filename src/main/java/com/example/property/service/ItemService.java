package com.example.property.service;

import com.example.property.auth.info.AuthInfoService;
import com.example.property.common.SearchCriteria;
import com.example.property.exception.exceptions.ItemNotFoundException;
import com.example.property.exception.exceptions.UserNotFoundException;
import com.example.property.model.*;
import com.example.property.page.*;
import com.example.property.page.request.ItemPageRequest;
import com.example.property.page.request.ItemPageRequestDTO;
import com.example.property.page.request.ItemPageRequestMapper;
import com.example.property.service.dto.*;
import com.example.property.service.mapper.ItemMapper;
import com.example.property.service.repository.*;
import lombok.AllArgsConstructor;
import org.json.JSONArray;

import com.example.property.service.mapper.TypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

@Service
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final TypeService typeService;
    private final ItemMapper itemMapper;
    private final TypeMapper typeMapper;
    private final UserRepository userRepository;
    private final AuthInfoService authInfoService;
    private final ItemPageRequestMapper itemPageRequestMapper;
    private final ItemPageMapper itemPageMapper;

    public ItemPageDTO getItemList(ItemPageRequestDTO itemPageRequestDTO, Authentication authentication) {

        ItemPageRequest itemPageRequest = itemPageRequestMapper.toItemPageRequest(itemPageRequestDTO);
        JSONArray filterJsonArray = new JSONArray(itemPageRequest.getFilterString());

        Pageable pageable = PageRequest.of(
                itemPageRequest.getPageNumber(),
                itemPageRequest.getPageSize(),
                Sort.by(itemPageRequest.getSortByKey())
        );

        User user = authInfoService.getUserByAuthentication(authentication);

        ItemSpecification[] itemSpecifications = new ItemSpecification[filterJsonArray.length()];
        Specification specification = null;

        for (int i = 0; i < filterJsonArray.length(); i++) {
            String filterKey = filterJsonArray.getJSONArray(i).get(0).toString();
            Object filterValue = filterJsonArray.getJSONArray(i).get(1).toString();
            String operation = ":";

             if (filterKey.compareTo("cost") == 0) {
                operation = ">";
            }

            else if (filterKey.compareTo("owner") == 0) {
                filterValue = user;
            }

            else if (filterKey.compareTo("type") == 0) {
                filterValue = typeMapper.toType(typeService.getTypeById(Long.parseLong((String)filterValue), authentication));
            }

            itemSpecifications[i] = new ItemSpecification(new SearchCriteria(filterKey, operation, (Object) filterValue));
            if (specification == null) specification = Specification.where(itemSpecifications[i]);
            else specification = specification.and(itemSpecifications[i]);
        }

        ItemSpecification isDeleteSpecification = new ItemSpecification(new SearchCriteria("isDeleted",":",false));
        if (specification == null) specification = Specification.where(isDeleteSpecification);
        else specification = specification.and(isDeleteSpecification);

        Page page = itemRepository.findAll(specification, pageable);
        ItemPage itemPage = ItemPage.builder()
                .code(200)
                .status("ok")
                .items(itemMapper.toItemDTOs(page.getContent()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();

        return itemPageMapper.toItemPageDTO(itemPage);
    }

    public ItemDTO getItemById(Long id, Authentication authentication) {
        Item item = itemRepository.findById(id).orElseThrow(ItemNotFoundException::new);
        return itemMapper.toItemDTO(item);
    }

    public ItemDTO saveItem(Long id, ItemDTO itemDTO, Authentication authentication) {
        Item item = itemMapper.toItem(itemDTO);
        if (id != null) {
            item.setId(id);
            User owner = userRepository.findById(itemDTO.getOwnerId()).orElseThrow(UserNotFoundException::new);
            item.setOwner(owner);
            if (!allowEditItems(authentication, item)) {
                throw new AccessDeniedException("The advertisement does not belong to the user");
            }
        }
        else {
            item.setOwner(authInfoService.getUserByAuthentication(authentication));
        }
        item.setIsDeleted(false);
        TypeDTO typeDTO = typeService.getTypeById(itemDTO.getTypeId(), authentication);
        item.setType(typeMapper.toType(typeDTO));
        return itemMapper.toItemDTO(itemRepository.save(item));
    }


    public void deleteItem(Long itemId, Authentication authentication) {
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        if (!allowEditItems(authentication, item)) {
            throw new AccessDeniedException("The advertisement does not belong to the user");
        }
        item.setIsDeleted(true);
        itemRepository.save(item);
    }

    public boolean allowEditItems(Authentication authentication, Item item) {
        User currentUser = authInfoService.getUserByAuthentication(authentication);
        return item.getOwner().getId().equals(currentUser.getId()) || currentUser.getRoles().contains(Role.ADMIN);
    }
}
