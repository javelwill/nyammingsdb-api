package com.javelwilson.nyammingsdb.controller;

import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import com.javelwilson.nyammingsdb.model.RestaurantRequestModel;
import com.javelwilson.nyammingsdb.model.RestaurantResponseModel;
import com.javelwilson.nyammingsdb.service.RestaurantService;
import com.javelwilson.nyammingsdb.service.RestaurantServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
