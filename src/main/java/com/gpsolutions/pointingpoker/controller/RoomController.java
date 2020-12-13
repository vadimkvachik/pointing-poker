package com.gpsolutions.pointingpoker.controller;

import com.gpsolutions.pointingpoker.dto.RoomDto;
import com.gpsolutions.pointingpoker.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity<List<RoomDto>> getAll() {
        return new ResponseEntity<>(roomService.getAllRooms(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@Valid @RequestBody final RoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateRoom(@Valid @RequestBody final RoomDto roomDto) {
        return roomService.updateRoom(roomDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRoom(@PathVariable("id") final String id) {
        roomService.deleteRoom(id);
    }
}
