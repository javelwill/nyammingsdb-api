package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.MenuItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends CrudRepository<MenuItemEntity, Long> {
}
