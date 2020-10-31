package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.LocationEntity;
import com.javelwilson.nyammingsdb.entity.OpeningHoursEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningHoursRepository extends CrudRepository<OpeningHoursEntity, Long> {
}
