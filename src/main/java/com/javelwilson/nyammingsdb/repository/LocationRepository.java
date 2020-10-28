package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.LocationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {
}
