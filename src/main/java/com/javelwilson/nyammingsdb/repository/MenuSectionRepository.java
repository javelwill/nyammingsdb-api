package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.MenuSectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuSectionRepository extends CrudRepository<MenuSectionEntity, Long> {
}
