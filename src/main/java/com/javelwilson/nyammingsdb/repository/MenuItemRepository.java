package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.MenuItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface MenuItemRepository extends CrudRepository<MenuItemEntity, Long> {
}
