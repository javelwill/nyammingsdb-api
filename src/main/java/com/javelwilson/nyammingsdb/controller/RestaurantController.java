package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import com.javelwilson.nyammingsdb.model.RestaurantRequestModel;
import com.javelwilson.nyammingsdb.model.RestaurantResponseModel;
import com.javelwilson.nyammingsdb.service.RestaurantService;
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
public class RestaurantController {

    @Autowired
    RestaurantServiceImpl restaurantService;

    @PostMapping("/restaurants")
    public RestaurantResponseModel createRestaurant(@Valid @RequestBody RestaurantRequestModel restaurantRequestModel) {
        RestaurantResponseModel restaurantResponseModel;

        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantRequestModel, RestaurantDto.class);

        restaurantDto = restaurantService.createRestaurant(restaurantDto);

        restaurantResponseModel = modelMapper.map(restaurantDto, RestaurantResponseModel.class);

        return restaurantResponseModel;
    }

    @GetMapping("/restaurants")
    public List<RestaurantResponseModel> getRestaurants(@RequestParam(value = "page", defaultValue = "0") int page,
                                                  @RequestParam(value = "limit", defaultValue = "2") int limit) {

        List<RestaurantResponseModel> restaurantResponseModels = new ArrayList<>();

        List<RestaurantDto> restaurantDtos = restaurantService.getRestaurants(page, limit);

        Type listType = new TypeToken<List<RestaurantResponseModel>>() {}.getType();
        restaurantResponseModels = new ModelMapper().map(restaurantDtos, listType);

        return restaurantResponseModels;
    }

}
