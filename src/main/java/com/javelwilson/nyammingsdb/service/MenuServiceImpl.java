package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.MenuDto;
import com.javelwilson.nyammingsdb.entity.*;
import com.javelwilson.nyammingsdb.repository.MenuRepository;
import com.javelwilson.nyammingsdb.repository.RestaurantRepository;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private Utils utils;

    @Override
    public MenuDto createMenu(String restaurantId, MenuDto menuDto) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        ModelMapper modelMapper = new ModelMapper();
        MenuEntity menuEntity = modelMapper.map(menuDto, MenuEntity.class);

        menuEntity.setMenuId(utils.generateMenuId(30));
        menuEntity.setRestaurant(restaurantEntity);

        for (int i = 0; i < menuEntity.getMenuSections().size(); i++) {
            MenuSectionEntity menuSectionEntity = menuEntity.getMenuSections().get(i);
            menuSectionEntity.setMenuSectionId(utils.generateMenuSectionId(30));
            menuSectionEntity.setMenu(menuEntity);

            for (int j = 0; j < menuSectionEntity.getMenuItems().size(); j++) {
                MenuItemEntity menuItemEntity = menuSectionEntity.getMenuItems().get(j);
                menuItemEntity.setMenuItemId(utils.generateMenuItemId(30));
                menuItemEntity.setMenuSection(menuSectionEntity);

                for (int k = 0; k < menuItemEntity.getOffers().size(); k++) {
                    MenuItemOfferEntity menuItemOfferEntity = menuItemEntity.getOffers().get(k);
                    menuItemOfferEntity.setMenuItemOfferId(utils.generateMenuOfferId(30));
                    menuItemOfferEntity.setMenuItem(menuItemEntity);
                }
            }
        }

        menuEntity = menuRepository.save(menuEntity);
        menuDto = modelMapper.map(menuEntity, MenuDto.class);

        return menuDto;
    }

    @Override
    public List<MenuDto> getMenus(String restaurantId) {
        List<MenuDto> menusDto = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        if (restaurantEntity == null) {
            return menusDto;
        }

        Iterable<MenuEntity> menuEntities = menuRepository.findAllByRestaurant(restaurantEntity);

        for (MenuEntity menuEntity : menuEntities) {
            menusDto.add(modelMapper.map(menuEntity, MenuDto.class));
        }

        return menusDto;
    }

    @Override
    public MenuDto getMenu(String restaurantId, String menuId) {
        MenuDto menuDto = new MenuDto();

        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        if (restaurantEntity == null) {
            return menuDto;
        }

        MenuEntity menuEntity = menuRepository.findByRestaurantAndMenuId(restaurantEntity, menuId);

        if (menuEntity == null) {
            return menuDto;
        }

        ModelMapper modelMapper = new ModelMapper();
        menuDto = modelMapper.map(menuEntity, MenuDto.class);

        return menuDto;
    }
}
