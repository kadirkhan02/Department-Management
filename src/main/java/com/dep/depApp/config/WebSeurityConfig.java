package com.dep.depApp.config;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSeurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/department/getall").hasRole("admin")
                        .requestMatchers("/api/**").hasAnyRole("User", "admin")
                        .anyRequest()
                        .authenticated()

                )
//                .sessionManagement(session-> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
        .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }
    @Bean
    UserDetailsService myInMemoryUserDetailsService()
    {
        UserDetails userDetails= User.withUsername("user")
                .password(passwordEncoder().encode("1"))
                .roles("User")
                .build();

        UserDetails userDetails1=User.withUsername("admin")
                .password(passwordEncoder().encode("1")).roles("admin").build();


        return new InMemoryUserDetailsManager(userDetails,userDetails1);

    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
