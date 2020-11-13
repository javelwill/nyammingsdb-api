package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.PhotoDto;
import com.javelwilson.nyammingsdb.dto.RestaurantDto;
import com.javelwilson.nyammingsdb.entity.PhotoEntity;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import com.javelwilson.nyammingsdb.repository.LocationRepository;
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
    LocationRepository locationRepository;

    @Autowired
    Utils utils;

    @Override
    public List<RestaurantDto> getRestaurants(int page, int limit) {
        List<RestaurantDto> restaurantsDto = new ArrayList<>();

        if (page > 0) page = page - 1;
        if (limit > 50) limit = 50;

        Pageable pageable = PageRequest.of(page, limit);

        Page<RestaurantEntity> restaurantPages = restaurantRepository.findAll(pageable);
        List<RestaurantEntity> restaurantEntities = restaurantPages.getContent();

        for (RestaurantEntity restaurantEntity : restaurantEntities) {
            RestaurantDto restaurantDto = new RestaurantDto();
            BeanUtils.copyProperties(restaurantEntity, restaurantDto);
            restaurantsDto.add(restaurantDto);
        }

        return restaurantsDto;
    }

    @Override
    public RestaurantDto createRestaurant(RestaurantDto newRestaurant) {
        if (restaurantRepository.findByName(newRestaurant.getName()) != null)
            throw new RuntimeException("Record already exists");

        ModelMapper modelMapper = new ModelMapper();

        RestaurantEntity restaurantEntity = modelMapper.map(newRestaurant, RestaurantEntity.class);

        String publicRestaurantId = utils.generateUserId(30);

        restaurantEntity.setRestaurantId(publicRestaurantId);

        restaurantEntity = restaurantRepository.save(restaurantEntity);

        RestaurantDto restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);

        return restaurantDto;
    }

    @Override
    public RestaurantDto getRestaurant(String restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);

        return restaurantDto;
    }

    @Override
    public RestaurantDto patchRestaurant(String restaurantId, RestaurantDto restaurantUpdate) {
        String name = restaurantUpdate.getName();
        String description = restaurantUpdate.getDescription();
        String logo = restaurantUpdate.getLogo();
        Double starRating = restaurantUpdate.getStarRating();
        String photo1 = restaurantUpdate.getPhoto1();
        String photo2 = restaurantUpdate.getPhoto2();
        String photo3 = restaurantUpdate.getPhoto3();
        Boolean acceptsReservations = restaurantUpdate.getAcceptsReservations();
        Boolean hasPos = restaurantUpdate.getHasPos();
        Boolean hasMenu = restaurantUpdate.getHasMenu();
        Double priceRange = restaurantUpdate.getPriceRange();
        String email = restaurantUpdate.getEmail();
        String telephone = restaurantUpdate.getTelephone();
        String faxNumber = restaurantUpdate.getFaxNumber();
        String slogan = restaurantUpdate.getSlogan();
        String address = restaurantUpdate.getAddress();

        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        if (restaurantEntity == null) {
            throw new RuntimeException("Record not found");
        }

        if (name != null) {
            restaurantEntity.setName(name);
        }

        if (description != null) {
            restaurantEntity.setDescription(description);
        }

        if (logo != null) {
            restaurantEntity.setLogo(logo);
        }

        if (starRating != null) {
            restaurantEntity.setStarRating(starRating);
        }

        if (photo1 != null) {
            restaurantEntity.setPhoto1(photo1);
        }

        if (photo2 != null) {
            restaurantEntity.setPhoto2(photo2);
        }

        if (photo3 != null) {
            restaurantEntity.setPhoto3(photo3);
        }

        if (acceptsReservations != null) {
            restaurantEntity.setAcceptsReservations(acceptsReservations);
        }

        if (hasMenu != null) {
            restaurantEntity.setHasMenu(hasMenu);
        }

        if (hasPos != null) {
            restaurantEntity.setHasPos(hasPos);
        }

        if (priceRange != null) {
            restaurantEntity.setPriceRange(priceRange);
        }

        if (email != null) {
            restaurantEntity.setEmail(email);
        }

        if (telephone != null) {
            restaurantEntity.setTelephone(telephone);
        }

        if (faxNumber != null) {
            restaurantEntity.setFaxNumber(faxNumber);
        }

        if (slogan != null) {
            restaurantEntity.setSlogan(slogan);
        }

        if (address != null) {
            restaurantEntity.setAddress(slogan);
        }


        restaurantEntity = restaurantRepository.save(restaurantEntity);

        ModelMapper modelMapper = new ModelMapper();

        RestaurantDto restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);

        return restaurantDto;
    }

    @Override
    public RestaurantDto deleteRestaurant(String restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        restaurantRepository.delete(restaurantEntity);

        if (restaurantEntity == null) {
            throw new RuntimeException("Record not found");
        }

        ModelMapper modelMapper = new ModelMapper();
        RestaurantDto restaurantDto = modelMapper.map(restaurantEntity, RestaurantDto.class);

        return restaurantDto;
    }
}
