package com.gpsolutions.pointingpoker.repository;

import com.gpsolutions.pointingpoker.entity.UserActivationTokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserActivationTokenRepository extends MongoRepository<UserActivationTokenEntity, String> {

    Optional<UserActivationTokenEntity> findByToken(final String token);

    void deleteAllByEmailIgnoreCase(final String email);

    void deleteAllByCreatedOnBefore(final LocalDate date);
}
