package com.hospital.main.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.hospital.main.service.impl.CustomUserDetailService;
import com.hospital.main.service.impl.DoctorDetailsServiceImpl;
import com.hospital.main.service.impl.HospitalDetailServiceImpl;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {

    @Autowired
    @Qualifier("userDetailsService")
    private CustomUserDetailService customUserDetailService;

    @Autowired
    @Qualifier("doctorDetailsService")
    private DoctorDetailsServiceImpl doctorDetailsService;

    @Autowired
    @Qualifier("hospitalDetailsService")
    private HospitalDetailServiceImpl hospitalDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(
            @Qualifier("userDetailsService") UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider doctorAuthenticationProvider(
            @Qualifier("doctorDetailsService") UserDetailsService doctorDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(doctorDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider hospitalAuthenticationProvider(
            @Qualifier("hospitalDetailsService") UserDetailsService hospitalDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(hospitalDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration,
            DaoAuthenticationProvider userAuthenticationProvider,
            DaoAuthenticationProvider doctorAuthenticationProvider,
            DaoAuthenticationProvider hospitalAuthenticationProvider) throws Exception {
        return new ProviderManager(
                Arrays.asList(
                        userAuthenticationProvider,
                        doctorAuthenticationProvider,
                        hospitalAuthenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(request -> {

            request.requestMatchers("/user/**").hasRole("PATIENT")
                    .requestMatchers("/hospital/**").hasRole("HOSPITAL")
                    .requestMatchers("/doctor/**").hasRole("DOCTOR")
                    .anyRequest().authenticated();
        });

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
