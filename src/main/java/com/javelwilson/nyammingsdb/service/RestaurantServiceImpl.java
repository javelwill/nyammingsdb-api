package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.LocationDto;
import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import com.javelwilson.nyammingsdb.repository.RestaurantRepository;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    Utils utils;

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurant) {
        if (restaurantRepository.findByName(restaurant.getName()) != null)
            throw new RuntimeException("Record already exists");

        for (int i = 0; i < restaurant.getLocations().size(); i++) {
            LocationDto location = restaurant.getLocations().get(i);
            location.setRestaurant(restaurant);
            location.setLocationId(utils.generateLocationId(30));
            restaurant.getLocations().set(i, location);
        }

        ModelMapper modelMapper = new ModelMapper();
        RestaurantEntity restaurantEntity = modelMapper.map(restaurant, RestaurantEntity.class);

        String publicRestaurantId = utils.generateUserId(30);
        restaurantEntity.setRestaurantId(publicRestaurantId);

        RestaurantEntity savedRestaurantEntity = restaurantRepository.save(restaurantEntity);

        RestaurantDto returnValue = modelMapper.map(savedRestaurantEntity, RestaurantDto.class);

        return returnValue;
    }

    @Override
    public List<RestaurantDto> getRestaurants(int page, int limit) {
        List<RestaurantDto> restaurantDtos = new ArrayList<>();

        if (page > 0) page = page - 1;

        Pageable pageable = PageRequest.of(page, limit);

        Page<RestaurantEntity> restaurantsPage = restaurantRepository.findAll(pageable);
        List<RestaurantEntity> restaurants = restaurantsPage.getContent();

        for (RestaurantEntity restaurantEntity: restaurants) {
            RestaurantDto restaurantDto = new RestaurantDto();
            BeanUtils.copyProperties(restaurantEntity, restaurantDto);
            restaurantDtos.add(restaurantDto);
        }

        return restaurantDtos;
    }
}
