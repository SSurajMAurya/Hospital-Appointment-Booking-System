package com.hospital.main.requestHelper;

import com.hospital.main.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDoctorRequest {

    private String name;

    private String email;

    private String specialization;

    private String phoneNumber;

    private boolean isAvailable;

    private String hospitalEmail;
    
    private String password;

    private Long departmentId;
      
    private Role role;

}
