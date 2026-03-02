package com.userManagementSystem.appconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Security {

    private final JwtFilter jwtFilter;

    public Security(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test1").hasAuthority("ADMIN")
                        .requestMatchers("/test2").hasAuthority("USER")
                        .anyRequest().permitAll());


        httpSecurity.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


    @Bean
 public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
