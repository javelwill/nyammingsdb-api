package com.javelwilson.nyammingsdb.service;

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
    public List<RestaurantDto> getRestaurants(int page, int limit) {
        List<RestaurantDto> restaurantDtos = new ArrayList<>();

        if (page > 0) page = page - 1;

        Pageable pageable = PageRequest.of(page, limit);

        Page<RestaurantEntity> restaurantsPage = restaurantRepository.findAll(pageable);
        List<RestaurantEntity> restaurants = restaurantsPage.getContent();

        for (RestaurantEntity restaurantEntity : restaurants) {
            RestaurantDto restaurantDto = new RestaurantDto();
            BeanUtils.copyProperties(restaurantEntity, restaurantDto);
            restaurantDtos.add(restaurantDto);
        }

        return restaurantDtos;
    }

    @Override
    public RestaurantDto createRestaurant(RestaurantDto newRestaurant) {
        if (restaurantRepository.findByName(newRestaurant.getName()) != null)
            throw new RuntimeException("Record already exists");

        ModelMapper modelMapper = new ModelMapper();
        RestaurantEntity restaurantEntity = modelMapper.map(newRestaurant, RestaurantEntity.class);

        String publicRestaurantId = utils.generateUserId(30);
        restaurantEntity.setRestaurantId(publicRestaurantId);

        RestaurantEntity savedRestaurantEntity = restaurantRepository.save(restaurantEntity);

        RestaurantDto restaurantDto = modelMapper.map(savedRestaurantEntity, RestaurantDto.class);

        return restaurantDto;
    }

    @Override
    public RestaurantDto getRestaurant(String restaurantId) {

        RestaurantDto restaurantDto;

        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        ModelMapper modelMapper = new ModelMapper();
        restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);

        return restaurantDto;
    }

    @Override
    public RestaurantDto updateRestaurant(String restaurantId, RestaurantDto restaurantUpdate) {

        String name = restaurantUpdate.getName();
        String description = restaurantUpdate.getDescription();
        String logo = restaurantUpdate.getLogo();
        int rating = restaurantUpdate.getRating();

        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        if (restaurantEntity == null) {
            throw new RuntimeException("Record not found");
        }

        if (name != null) {
            restaurantEntity.setName(restaurantUpdate.getName());
        }

        if (description != null) {
            restaurantEntity.setDescription(restaurantUpdate.getDescription());
        }

        if (logo != null) {
            restaurantEntity.setLogo(restaurantUpdate.getLogo());
        }

        if (rating != restaurantEntity.getRating()) {
            restaurantEntity.setRating(restaurantUpdate.getRating());
        }

        restaurantEntity = restaurantRepository.save(restaurantEntity);

        ModelMapper modelMapper = new ModelMapper();
        RestaurantDto restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);

        return restaurantDto;
    }
}
