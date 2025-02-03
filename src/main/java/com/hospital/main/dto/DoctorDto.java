package com.hospital.main.dto;


import java.util.List;


import com.hospital.main.entities.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

     private Long id;

    private String name;

    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid User Email !!")
    @NotBlank(message = "Email is required !!")
    private String email;

    private String specialization;

    private String phoneNumber;

    private boolean isAvailable;

    
    private List<AppointmentDto> appointments;

   
    private Long departmentId;

    private String password;

    
    private Long hospitalId;

    private Role role;

}
