package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<RestaurantEntity, Long> {
    RestaurantEntity findByName(String name);
    RestaurantEntity findByRestaurantId(String restaurantId);
}
