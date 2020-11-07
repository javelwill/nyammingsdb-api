package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.MenuItemOfferEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemOfferRepository extends CrudRepository<MenuItemOfferEntity, Long> {
}
