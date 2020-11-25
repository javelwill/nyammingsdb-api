package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
