package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Long> {
    AuthorityEntity findByName(String name);
}
