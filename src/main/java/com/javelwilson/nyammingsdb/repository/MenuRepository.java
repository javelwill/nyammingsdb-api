package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.MenuEntity;
import com.javelwilson.nyammingsdb.entity.RestaurantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends CrudRepository<MenuEntity, Long> {
    List<MenuEntity> findAllByRestaurant(RestaurantEntity restaurantEntity);
    MenuEntity findByRestaurantAndMenuId(RestaurantEntity restaurantEntity, String menuId);

}
