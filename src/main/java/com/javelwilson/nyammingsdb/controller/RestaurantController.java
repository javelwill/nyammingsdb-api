package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.LocationDto;
import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.model.LocationResponseModel;
import com.javelwilson.nyammingsdb.model.RestaurantRequestModel;
import com.javelwilson.nyammingsdb.model.RestaurantResponseModel;
import com.javelwilson.nyammingsdb.service.LocationServiceImpl;
import com.javelwilson.nyammingsdb.service.RestaurantServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantServiceImpl restaurantService;

    @Autowired
    LocationServiceImpl locationService;

    @PostMapping()
    public RestaurantResponseModel createRestaurant(@Valid @RequestBody RestaurantRequestModel restaurantRequestModel) {
        RestaurantResponseModel restaurantResponseModel;

        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantRequestModel, RestaurantDto.class);

        restaurantDto = restaurantService.createRestaurant(restaurantDto);

        restaurantResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantResponseModel;
    }

    @GetMapping()
    public List<RestaurantResponseModel> getRestaurants(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "limit", defaultValue = "2") int limit) {

        List<RestaurantResponseModel> restaurantsResponseModel;

        List<RestaurantDto> restaurantsDto = restaurantService.getRestaurants(page, limit);

        Type listType = new TypeToken<List<RestaurantResponseModel>>() {}.getType();
        restaurantsResponseModel = new ModelMapper().map(restaurantsDto, listType);

        return restaurantsResponseModel;
    }

    @GetMapping("/{id}")
    public RestaurantResponseModel getRestaurant(@PathVariable String id) {

        RestaurantResponseModel restaurantsResponseModel;

        RestaurantDto restaurantsDto = restaurantService.getRestaurant(id);

        ModelMapper modelMapper = new ModelMapper();
        restaurantsResponseModel = modelMapper.map(restaurantsDto, RestaurantResponseModel.class);

        return restaurantsResponseModel;
    }

    @GetMapping("/{id}/locations")
    public List<LocationResponseModel> getRestaurantLocations(@PathVariable String id) {
        List<LocationResponseModel> locationsResponseModel;

        List<LocationDto> locationsDto = locationService.getLocations(id);

        Type listType = new TypeToken<List<LocationResponseModel>>() {}.getType();
        locationsResponseModel = new ModelMapper().map(locationsDto, listType);

        return locationsResponseModel;
    }

    @GetMapping("/{restaurantId}/locations/{locationId}")
    public LocationResponseModel getRestaurantLocation(@PathVariable String restaurantId, @PathVariable String locationId) {
        LocationResponseModel locationResponseModel;

        LocationDto locationsDto = locationService.getLocation(restaurantId, locationId);

        locationResponseModel = new ModelMapper().map(locationsDto, LocationResponseModel.class);

        return locationResponseModel;
    }

}
