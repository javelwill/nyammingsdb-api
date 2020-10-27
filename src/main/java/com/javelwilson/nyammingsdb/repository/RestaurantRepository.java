package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<RestaurantEntity, Long> {
}
