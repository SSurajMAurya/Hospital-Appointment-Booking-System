package com.hospital.main.config;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.hospital.main.security.JwtAuthenticationEntryPoint;
import com.hospital.main.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;




@EnableWebSecurity(debug = true)
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(httpSecurityCsrfCongigure ->
        httpSecurityCsrfCongigure.configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration corsConfiguration = new CorsConfiguration();

                //origins
                //for single origin 
                // corsConfiguration.addAllowedOrigin("http://localhost:5173");

                //for multiple origind
                corsConfiguration.setAllowedOriginPatterns(List.of("*"));

                //methods
                corsConfiguration.setAllowedMethods(List.of("*"));
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setMaxAge(3000L);

                return corsConfiguration;
            }

        }));

        httpSecurity.csrf(httpSecurityCsrfCongigure -> httpSecurityCsrfCongigure.disable());


        httpSecurity.authorizeHttpRequests(request -> {

            // request.requestMatchers("/user/**").hasRole("PATIENT")
            //         .requestMatchers("/hospital/**").hasRole("HOSPITAL")
            //         .requestMatchers("/doctor/**").hasRole("DOCTOR")
            //         .anyRequest().authenticated();

            request.requestMatchers( "/user/**").permitAll()
                   .requestMatchers(HttpMethod.GET , "/user").authenticated()
                   .requestMatchers(HttpMethod.POST , "/hospital").permitAll()
                   .requestMatchers(HttpMethod.POST , "/doctor").hasRole("HOSPITAL")
                   .requestMatchers( "/auth/**").permitAll()
                   .requestMatchers( "/auth/login-with-google-user").permitAll()
                   .anyRequest().authenticated();

            // request.anyRequest().authenticated();  

            // request.anyRequest().permitAll();
        });

      
        httpSecurity.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

     @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
