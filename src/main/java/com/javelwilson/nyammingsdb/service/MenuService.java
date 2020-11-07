package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.MenuDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface MenuService {
    MenuDto createMenu(String restaurantId, MenuDto menuDto);
    List<MenuDto> getMenus(String restaurantId);
    MenuDto getMenu (String restaurantId, String menuId);

}
