package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.LocationEntity;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {
    List<LocationEntity> findAllByRestaurant(RestaurantEntity restaurantEntity);
    LocationEntity findByRestaurantAndLocationId(RestaurantEntity restaurantEntity, String locationId);
    LocationEntity findByLocationId(String locationId);
}
