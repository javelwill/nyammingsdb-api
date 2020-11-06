package com.javelwilson.nyammingsdb.service;

import com.javelwilson.nyammingsdb.dto.LocationDto;
import com.javelwilson.nyammingsdb.entity.LocationEntity;
import com.javelwilson.nyammingsdb.entity.OpeningHoursEntity;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import com.javelwilson.nyammingsdb.repository.LocationRepository;
import com.javelwilson.nyammingsdb.repository.RestaurantRepository;
import com.javelwilson.nyammingsdb.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private Utils utils;

    @Override
    public LocationDto getLocation(String restaurantId, String locationId) {
        LocationDto locationDto = new LocationDto();

        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);
        if (restaurantEntity == null) {
            return locationDto;
        }

        LocationEntity locationEntity = locationRepository.findByRestaurantAndLocationId(restaurantEntity, locationId);
        ModelMapper modelMapper = new ModelMapper();
        locationDto = modelMapper.map(locationEntity, LocationDto.class);

        return locationDto;
    }

    @Override
    public List<LocationDto> getLocations(String restaurantId) {
        List<LocationDto> locationsDto = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);
        if (restaurantEntity == null) {
            return locationsDto;
        }

        Iterable<LocationEntity> locationEntities = locationRepository.findAllByRestaurant(restaurantEntity);

        for (LocationEntity locationEntity : locationEntities) {
            locationsDto.add(modelMapper.map(locationEntity, LocationDto.class));
        }

        return locationsDto;
    }

    @Override
    public LocationDto createLocation(String restaurantId, LocationDto locationDto) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByRestaurantId(restaurantId);

        ModelMapper modelMapper = new ModelMapper();
        LocationEntity locationEntity = modelMapper.map(locationDto, LocationEntity.class);

        for (int i = 0; i < locationEntity.getOpeningHours().size(); i++){
            OpeningHoursEntity openingHoursEntity = locationEntity.getOpeningHours().get(i);
            openingHoursEntity.setLocation(locationEntity);
            locationEntity.getOpeningHours().set(i, openingHoursEntity);
        }

        locationEntity.setLocationId(utils.generateLocationId(30));
        locationEntity.setRestaurant(restaurantEntity);

        locationEntity = locationRepository.save(locationEntity);
        locationDto = modelMapper.map(locationEntity, LocationDto.class);

        return locationDto;
    }

}
