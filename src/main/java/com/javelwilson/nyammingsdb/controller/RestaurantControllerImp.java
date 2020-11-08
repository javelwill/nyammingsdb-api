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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/restaurants")
public class RestaurantControllerImp implements RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private MenuServiceImpl menuService;

    public List<RestaurantResponseModel> getRestaurants(int page, int limit) {
        List<RestaurantDto> restaurantsDto = restaurantService.getRestaurants(page, limit);

        Type listType = new TypeToken<List<RestaurantResponseModel>>() {
        }.getType();
        List<RestaurantResponseModel> restaurantsResponseModel = new ModelMapper().map(restaurantsDto, listType);

        return restaurantsResponseModel;
    }

    public RestaurantResponseModel createRestaurant(RestaurantRequestModel restaurantRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantRequestModel, RestaurantDto.class);

        restaurantDto = restaurantService.createRestaurant(restaurantDto);

        RestaurantResponseModel restaurantResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantResponseModel;
    }

    public RestaurantResponseModel patchRestaurant(String id, RestaurantRequestModel restaurantRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantRequestModel, RestaurantDto.class);

        restaurantDto = restaurantService.patchRestaurant(id, restaurantDto);

        RestaurantResponseModel restaurantsResponseModel;

        restaurantsResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantsResponseModel;
    }

    public RestaurantResponseModel getRestaurant(String id) {
        RestaurantDto restaurantsDto = restaurantService.getRestaurant(id);

        ModelMapper modelMapper = new ModelMapper();

        RestaurantResponseModel restaurantsResponseModel = modelMapper.map(restaurantsDto, RestaurantResponseModel.class);

        return restaurantsResponseModel;
    }

    public RestaurantResponseModel deleteRestaurant(String id) {
        RestaurantDto restaurantDto = restaurantService.deleteRestaurant(id);

        ModelMapper modelMapper = new ModelMapper();

        RestaurantResponseModel restaurantsResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantsResponseModel;
    }

    public LocationResponseModel addLocation(String id, LocationRequestModel locationRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        LocationDto locationDto = modelMapper.map(locationRequestModel, LocationDto.class);

        locationDto = locationService.createLocation(id, locationDto);

        LocationResponseModel locationResponseModel = modelMapper.map(locationDto, LocationResponseModel.class);

        return locationResponseModel;
    }

    public List<LocationResponseModel> getRestaurantLocations(String id) {
        List<LocationDto> locationsDto = locationService.getLocations(id);

        Type listType = new TypeToken<List<LocationResponseModel>>() {
        }.getType();
        List<LocationResponseModel> locationsResponseModel = new ModelMapper().map(locationsDto, listType);

        return locationsResponseModel;
    }

    public LocationResponseModel getRestaurantLocation(String restaurantId, String locationId) {
        LocationDto locationsDto = locationService.getLocation(restaurantId, locationId);

        LocationResponseModel locationResponseModel = new ModelMapper().map(locationsDto, LocationResponseModel.class);

        return locationResponseModel;
    }

    public MenuResponseModel addMenu(String id, MenuRequestModel menuRequestModel) {
        ModelMapper modelMapper = new ModelMapper();

        MenuDto menuDto = modelMapper.map(menuRequestModel, MenuDto.class);

        menuDto = menuService.createMenu(id, menuDto);

        MenuResponseModel menuResponseModel = modelMapper.map(menuDto, MenuResponseModel.class);

        return menuResponseModel;
    }


    public List<MenuResponseModel> getRestaurantMenus(String id) {
        List<MenuDto> menusDto = menuService.getMenus(id);

        Type listType = new TypeToken<List<MenuResponseModel>>() {
        }.getType();
        List<MenuResponseModel> menusResponseModel = new ModelMapper().map(menusDto, listType);

        return menusResponseModel;
    }

    public MenuResponseModel getRestaurantMenu(String restaurantId, String menuId) {
        MenuDto menuDto = menuService.getMenu(restaurantId, menuId);

        MenuResponseModel menuResponseModel = new ModelMapper().map(menuDto, MenuResponseModel.class);

        return menuResponseModel;
    }

}
