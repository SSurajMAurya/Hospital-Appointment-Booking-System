package com.hospital.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hospital.main.entities.Doctor;
import com.hospital.main.repository.DoctorRepository;

@Service("doctorDetailsService")
public class DoctorDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No account found with email :" + username));
        return doctor;

    }

}
