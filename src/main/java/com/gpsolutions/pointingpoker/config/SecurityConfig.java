package com.gpsolutions.pointingpoker.config;

import com.gpsolutions.pointingpoker.config.security.JwtFilter;
import com.gpsolutions.pointingpoker.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole(UserRoleEnum.ADMIN.toString())
            .antMatchers("/user/**",
                         "/room/**").hasAnyRole(UserRoleEnum.USER.toString(), UserRoleEnum.ADMIN.toString())
            .antMatchers("/register/**",
                         "/login/**",
                         "/activation/**",
                         "/swagger-ui.html#!/**").permitAll()
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
