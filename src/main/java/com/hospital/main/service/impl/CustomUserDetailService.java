package com.hospital.main.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hospital.main.entities.Admin;
import com.hospital.main.entities.CustomUserDetails;
import com.hospital.main.entities.Doctor;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.User;
import com.hospital.main.repository.AdminRepository;
import com.hospital.main.repository.DoctorRepository;
import com.hospital.main.repository.HospitalRepository;
import com.hospital.main.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        
        // Optional<User> user = userRepository.findByEmail(username);
        // if(user.isPresent()){
        //     return new CustomUserDetails(user);
        // }

        //  Optional<Doctor> doctor = doctorRepository.findByEmail(username);
        // if (doctor.isPresent()) {
        //     return new CustomUserDetails(doctor);
        // }

        // // 3️⃣ Check in Hospital table
        // Optional<Hospital> hospital = hospitalRepository.findByEmail(username);
        // if (hospital.isPresent()) {
        //     return new CustomUserDetails(hospital);
        // }

        // Optional<Admin> admin = adminRepository.findByEmail(username);
        // if(admin.isPresent()){
        //     return new CustomUserDetails(admin);
        // }

        

        // throw new UsernameNotFoundException("User not found with username: " + username);


        User user = userRepository.findByEmail(username).orElse(null);
        if (user != null) {
            return new CustomUserDetails(user);
        }
    
        Doctor doctor = doctorRepository.findByEmail(username).orElse(null);
        if (doctor != null) {
            return new CustomUserDetails(doctor);
        }
    
        Hospital hospital = hospitalRepository.findByEmail(username).orElse(null);
        if (hospital != null) {
            return new CustomUserDetails(hospital);
        }

        Admin admin = adminRepository.findByEmail(username).orElse(null);
        if (admin != null) {
            return new CustomUserDetails(admin);
        }
    
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
    }

