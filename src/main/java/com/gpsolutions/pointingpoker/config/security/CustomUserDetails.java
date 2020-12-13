package com.gpsolutions.pointingpoker.config.security;

import com.gpsolutions.pointingpoker.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";
    private String email;
    private String password;
    private boolean active;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails userEntityToCustomUserDetailsConverter(final UserEntity userEntity) {
        final CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.email = userEntity.getEmail();
        customUserDetails.password = userEntity.getPassword();
        customUserDetails.active = userEntity.isActive();
        customUserDetails.grantedAuthorities =
            Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + userEntity.getRole().toString()));
        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
