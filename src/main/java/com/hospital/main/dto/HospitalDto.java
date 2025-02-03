package com.hospital.main.dto;
import java.util.List;
import com.hospital.main.entities.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDto {

    private Long id;

    private String hospitalName;

    private String address;

    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid User Email !!")
    @NotBlank(message = "Email is required !!")
    private String email;

    private String description;

    private String phoneNumber;

    private String password;

    private boolean isOpen;

    private List<DepartmentsDto> departments;

    private List<AppointmentDto> appointments;

    private List<DoctorDto> doctors;

    private Role role;

}

