package com.gpsolutions.pointingpoker.repository;

import com.gpsolutions.pointingpoker.entity.RoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<RoomEntity, String> {
}
