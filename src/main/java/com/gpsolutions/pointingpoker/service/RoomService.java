package com.gpsolutions.pointingpoker.service;

import com.gpsolutions.pointingpoker.dto.RoomDto;
import com.gpsolutions.pointingpoker.entity.RoomEntity;
import com.gpsolutions.pointingpoker.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private static final String USER_IS_NOT_ADMIN = "User isn't admin of this room";
    private final RoomRepository repository;
    private final ModelMapper modelMapper;

    public List<RoomDto> getAllRooms() {
        return repository.findAll().stream().map(room -> modelMapper.map(room, RoomDto.class))
            .collect(Collectors.toList());
    }

    public RoomEntity getRoomById(final String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public ResponseEntity<String> createRoom(final RoomDto roomDto) {
        repository.save(modelMapper.map(roomDto, RoomEntity.class));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> updateRoom(final RoomDto roomDto) {
        final RoomEntity room = getRoomById(roomDto.getId());
        if (!room.getAdminId().equals(roomDto.getAdminId())) {
            return ResponseEntity.badRequest().body(USER_IS_NOT_ADMIN);
        }
        final String newStoryDescription = roomDto.getStoryDescription();
        final String newName = roomDto.getName();
        final List<String> userIds = roomDto.getUserIds();
        final List<Double> pointValues = room.getPointValues();
        if (!room.getStoryDescription().equals(newStoryDescription)) {
            room.setStoryDescription(newStoryDescription);
        }
        if (!room.getName().equals(newName)) {
            room.setName(newName);
        }
        if (!room.getUserIds().equals(userIds)) {
            room.setUserIds(userIds);
        }
        if (!room.getPointValues().equals(pointValues)) {
            room.setPointValues(pointValues);
        }
        repository.save(room);
        return ResponseEntity.ok().build();
    }

    public void deleteRoom(final String id) {
        repository.deleteById(id);
    }
}
