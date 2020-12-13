package com.gpsolutions.pointingpoker.service;

import com.gpsolutions.pointingpoker.dto.UserDto;
import com.gpsolutions.pointingpoker.dto.UserUpdateDto;
import com.gpsolutions.pointingpoker.entity.UserEntity;
import com.gpsolutions.pointingpoker.enums.UserRoleEnum;
import com.gpsolutions.pointingpoker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gpsolutions.pointingpoker.util.RegExpUtil.PASSWORD_REGEXP;
import static com.gpsolutions.pointingpoker.util.RegExpUtil.isNotValidByRegexp;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String NEW_PASSWORD_IS_INCORRECT = "New password is incorrect";

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserActivationService userActivationService;

    public UserEntity getUserById(final String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserEntity getUserByEmail(final String email) {
        return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void saveUser(final UserEntity user) {
        repository.save(user);
    }

    public void deactivateUser(final String userId) {
        final UserEntity user = getUserById(userId);
        user.setActive(false);
        saveUser(user);
    }

    public ResponseEntity<String> createUser(final UserDto user) {
        final String email = user.getEmail();
        final UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setName(user.getName());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(UserRoleEnum.USER);
        repository.save(userEntity);
        return userActivationService.sendActivationEmail(email);
    }

    public Optional<UserEntity> findByEmailAndPassword(final String email, final String password) {
        final Optional<UserEntity> user = repository.findByEmailIgnoreCase(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public ResponseEntity<String> updateUser(final UserUpdateDto userUpdateDto) {
        final UserEntity user = getUserById(userUpdateDto.getId());
        final String newName = userUpdateDto.getName();
        final String newPassword = userUpdateDto.getPassword();
        if (StringUtils.isNotBlank(newPassword)) {
            if (isNotValidByRegexp(PASSWORD_REGEXP, newPassword)) {
                return ResponseEntity.badRequest().body(NEW_PASSWORD_IS_INCORRECT);
            }
            final String encryptedNewPassword = passwordEncoder.encode(newPassword);
            if (!encryptedNewPassword.equals(user.getPassword())) {
                user.setPassword(encryptedNewPassword);
            }
        }
        if (StringUtils.isNotBlank(newName) && !newName.equals(user.getName())) {
            user.setName(newName);
        }
        saveUser(user);
        return ResponseEntity.ok().build();
    }
}
