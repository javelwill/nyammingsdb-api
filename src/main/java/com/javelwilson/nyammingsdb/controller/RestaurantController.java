package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.LocationDto;
import com.javelwilson.nyammingsdb.dto.MenuDto;
import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.model.*;
import com.javelwilson.nyammingsdb.service.LocationServiceImpl;
import com.javelwilson.nyammingsdb.service.MenuServiceImpl;
import com.javelwilson.nyammingsdb.service.RestaurantServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private MenuServiceImpl menuService;

    @GetMapping()
    public List<RestaurantResponseModel> getRestaurants(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<RestaurantDto> restaurantsDto = restaurantService.getRestaurants(page, limit);

        Type listType = new TypeToken<List<RestaurantResponseModel>>() {
        }.getType();
        List<RestaurantResponseModel> restaurantsResponseModel = new ModelMapper().map(restaurantsDto, listType);

        return restaurantsResponseModel;
    }

    @PostMapping()
    public RestaurantResponseModel createRestaurant(@Valid @RequestBody RestaurantRequestModel restaurantRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantRequestModel, RestaurantDto.class);

        restaurantDto = restaurantService.createRestaurant(restaurantDto);

        RestaurantResponseModel restaurantResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantResponseModel;
    }

    @PatchMapping("/{id}")
    public RestaurantResponseModel patchRestaurant(@PathVariable String id, @RequestBody RestaurantRequestModel restaurantRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantRequestModel, RestaurantDto.class);

        restaurantDto = restaurantService.patchRestaurant(id, restaurantDto);

        RestaurantResponseModel restaurantsResponseModel;

        restaurantsResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantsResponseModel;
    }

    @GetMapping("/{id}")
    public RestaurantResponseModel getRestaurant(@PathVariable String id) {
        RestaurantDto restaurantsDto = restaurantService.getRestaurant(id);

        ModelMapper modelMapper = new ModelMapper();

        RestaurantResponseModel restaurantsResponseModel = modelMapper.map(restaurantsDto, RestaurantResponseModel.class);

        return restaurantsResponseModel;
    }

    @DeleteMapping("/{id}")
    public RestaurantResponseModel deleteRestaurant(@PathVariable String id) {
        RestaurantDto restaurantDto = restaurantService.deleteRestaurant(id);

        ModelMapper modelMapper = new ModelMapper();

        RestaurantResponseModel restaurantsResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantsResponseModel;
    }

    @PostMapping("/{id}/locations")
    public LocationResponseModel addLocation(@PathVariable String id, @RequestBody LocationRequestModel locationRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        LocationDto locationDto = modelMapper.map(locationRequestModel, LocationDto.class);

        locationDto = locationService.createLocation(id, locationDto);

        LocationResponseModel locationResponseModel = modelMapper.map(locationDto, LocationResponseModel.class);

        return locationResponseModel;
    }

    @GetMapping("/{id}/locations")
    public List<LocationResponseModel> getRestaurantLocations(@PathVariable String id) {
        List<LocationDto> locationsDto = locationService.getLocations(id);

        Type listType = new TypeToken<List<LocationResponseModel>>() {
        }.getType();
        List<LocationResponseModel> locationsResponseModel = new ModelMapper().map(locationsDto, listType);

        return locationsResponseModel;
    }

    @GetMapping("/{restaurantId}/locations/{locationId}")
    public LocationResponseModel getRestaurantLocation(@PathVariable String restaurantId, @PathVariable String locationId) {
        LocationDto locationsDto = locationService.getLocation(restaurantId, locationId);

        LocationResponseModel locationResponseModel = new ModelMapper().map(locationsDto, LocationResponseModel.class);

        return locationResponseModel;
    }

    @PostMapping("/{id}/menus")
    public MenuResponseModel addMenu(@PathVariable String id, @RequestBody MenuRequestModel menuRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        MenuDto menuDto = modelMapper.map(menuRequestModel, MenuDto.class);

        menuDto = menuService.createMenu(id, menuDto);

        MenuResponseModel menuResponseModel = modelMapper.map(menuDto, MenuResponseModel.class);

        return menuResponseModel;
    }

    @GetMapping("/{id}/menus")
    public List<MenuResponseModel> getRestaurantMenus(@PathVariable String id) {
        List<MenuDto> menusDto = menuService.getMenus(id);

        Type listType = new TypeToken<List<MenuResponseModel>>() {
        }.getType();
        List<MenuResponseModel> menusResponseModel = new ModelMapper().map(menusDto, listType);

        return menusResponseModel;
    }

    @GetMapping("/{restaurantId}/menus/{menuId}")
    public MenuResponseModel getRestaurantMenu(@PathVariable String restaurantId, @PathVariable String menuId) {
        MenuDto menuDto = menuService.getMenu(restaurantId, menuId);

        MenuResponseModel menuResponseModel = new ModelMapper().map(menuDto, MenuResponseModel.class);

        return menuResponseModel;
    }

}
