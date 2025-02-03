package com.hospital.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hospital.main.entities.Hospital;
import com.hospital.main.repository.HospitalRepository;

@Service("hospitalDetailsService")
public class HospitalDetailServiceImpl implements UserDetailsService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hospital hospital = hospitalRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No account found with email :" + username));

        return hospital;
    }

}
