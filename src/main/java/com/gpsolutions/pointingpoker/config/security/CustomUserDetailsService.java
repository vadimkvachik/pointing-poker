package com.gpsolutions.pointingpoker.config.security;

import com.gpsolutions.pointingpoker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return CustomUserDetails.userEntityToCustomUserDetailsConverter(userService.getUserByEmail(email));
    }
}
