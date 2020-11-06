package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.LocationDto;
import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.model.LocationRequestModel;
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
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantServiceImpl restaurantService;

    @Autowired
    LocationServiceImpl locationService;

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

}
