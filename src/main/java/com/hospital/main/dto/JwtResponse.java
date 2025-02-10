package com.hospital.main.dto;

import com.hospital.main.entities.Doctor;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.Role;
import com.hospital.main.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private String token;
    private UserDto user;
    private HospitalDto hospital;
    private DoctorDto doctor;
    private AdminDto admin;
    private String refeshToken;
}
