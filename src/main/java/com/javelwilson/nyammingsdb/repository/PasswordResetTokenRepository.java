package com.javelwilson.nyammingsdb.repository;

import com.javelwilson.nyammingsdb.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findByToken(String token);
}
