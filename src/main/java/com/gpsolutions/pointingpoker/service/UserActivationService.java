package com.gpsolutions.pointingpoker.service;

import com.gpsolutions.pointingpoker.entity.UserActivationTokenEntity;
import com.gpsolutions.pointingpoker.entity.UserEntity;
import com.gpsolutions.pointingpoker.repository.UserActivationTokenRepository;
import com.gpsolutions.pointingpoker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static com.gpsolutions.pointingpoker.util.EmailTemplatesUtil.SUCCESSFUL_USER_ACTIVATION_SUBJECT;
import static com.gpsolutions.pointingpoker.util.EmailTemplatesUtil.SUCCESSFUL_USER_ACTIVATION_TEXT;
import static com.gpsolutions.pointingpoker.util.EmailTemplatesUtil.USER_ACTIVATION_SUBJECT;
import static com.gpsolutions.pointingpoker.util.EmailTemplatesUtil.USER_ACTIVATION_TEXT;
import static com.gpsolutions.pointingpoker.util.EmailTemplatesUtil.getTextWithLink;

@Service
@RequiredArgsConstructor
public class UserActivationService {

    private static final String CONFIRM = "Confirm";
    private final EmailSender emailSender;
    private final UserActivationTokenRepository userActivationTokenRepository;
    private final UserRepository userRepository;
    @Value("${app.url}")
    private String appUrl;

    @SneakyThrows
    public ResponseEntity<String> sendActivationEmail(final String email) {
        final String token = UUID.randomUUID().toString();
        userActivationTokenRepository.deleteAllByEmailIgnoreCase(email);
        userActivationTokenRepository.save(new UserActivationTokenEntity(token, email, LocalDate.now()));
        final String link = appUrl + "activation/" + token;
        final String emailText = getTextWithLink(USER_ACTIVATION_TEXT, link, CONFIRM);
        emailSender.send(USER_ACTIVATION_SUBJECT, emailText, email);
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    public ResponseEntity<String> activateUserByToken(final String token) {
        final String userEmail = userActivationTokenRepository.findByToken(token)
            .map(UserActivationTokenEntity::getEmail)
            .orElseThrow(() -> new RuntimeException("Requested token not found"));
        final Optional<UserEntity> userEntity = userRepository.findByEmailIgnoreCase(userEmail);
        if (userEntity.isPresent()) {
            final UserEntity user = userEntity.get();
            user.setActive(true);
            userRepository.save(user);
            userActivationTokenRepository.deleteAllByEmailIgnoreCase(userEmail);
            emailSender.send(SUCCESSFUL_USER_ACTIVATION_SUBJECT, SUCCESSFUL_USER_ACTIVATION_TEXT, userEmail);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //    @Scheduled(cron = "${periodicity.every-minute}")
    @Scheduled(cron = "${periodicity.every-day-at-3-am}")
    public void expiredTokenRemover() {
        userActivationTokenRepository.deleteAllByCreatedOnBefore(LocalDate.now());
    }
}
