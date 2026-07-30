package com.dep.depApp.config;


import com.dep.depApp.enums.Permission;
import com.dep.depApp.filters.JwtAuthFilter;
import com.dep.depApp.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.dep.depApp.enums.Role.ADMIN;
import static com.dep.depApp.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSeurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes=
            {
                    "/auth/**","home.html"
            };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                     .requestMatchers(HttpMethod.GET, "/api/department/**").hasRole("ADMIN")
                      .requestMatchers(HttpMethod.POST,"/api/department").hasAnyRole(ADMIN.name(),CREATOR.name())
                        .requestMatchers(HttpMethod.GET, "/api/department/**").hasAuthority(Permission.USER_CREATE.name())
                        .anyRequest()
                        .authenticated()

                )
              // .sessionManagement(session-> session
              //        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config->oauth2Config
                         .failureUrl("/log?error=true")
                       .successHandler(oAuth2SuccessHandler)
                );

                 ;
     //   .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService()
//    {
//        UserDetails userDetails= User.withUsername("user")
//                .password(passwordEncoder().encode("1"))
//                .roles("User")
//                .build();
//
//        UserDetails userDetails1=User.withUsername("admin")
//                .password(passwordEncoder().encode("1")).roles("admin").build();
//
//
//        return new InMemoryUserDetailsManager(userDetails,userDetails1);
//
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
