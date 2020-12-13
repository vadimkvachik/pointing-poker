package com.gpsolutions.pointingpoker.controller;

import com.gpsolutions.pointingpoker.config.security.JwtProvider;
import com.gpsolutions.pointingpoker.dto.LoginDto;
import com.gpsolutions.pointingpoker.dto.UserDto;
import com.gpsolutions.pointingpoker.dto.UserUpdateDto;
import com.gpsolutions.pointingpoker.entity.UserEntity;
import com.gpsolutions.pointingpoker.service.UserActivationService;
import com.gpsolutions.pointingpoker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserActivationService userActivationService;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody final UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/activation/{token}")
    public ResponseEntity<String> activateUserByToken(@PathVariable final String token) {
        return userActivationService.activateUserByToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginDto loginDto, final HttpServletResponse response) {
        final Optional<UserEntity> userEntity =
            userService.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (userEntity.isPresent()) {
            jwtProvider.addTokenToCookies(userEntity.get().getEmail(), response);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody final UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }

    @PatchMapping("/user/deactivate/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable final String id) {
        userService.deactivateUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
