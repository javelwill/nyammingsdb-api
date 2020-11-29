package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.ApplicationEntity;
import com.javelwilson.nyammingsdb.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
    List<ApplicationEntity> findAllByUser(UserEntity userEntity);
    ApplicationEntity findByUserAndApplicationId(UserEntity userEntity, String applicationId);
}
