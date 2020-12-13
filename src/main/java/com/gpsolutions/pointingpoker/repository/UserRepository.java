package com.gpsolutions.pointingpoker.repository;

import com.gpsolutions.pointingpoker.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByEmailIgnoreCase(final String email);
}