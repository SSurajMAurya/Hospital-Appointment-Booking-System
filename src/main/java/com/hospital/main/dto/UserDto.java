package com.hospital.main.dto;

import java.util.List;

import com.hospital.main.entities.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String name;

    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid User Email !!")
    @NotBlank(message = "Email is required !!")
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    private String address;

    @NotBlank(message = "Password is required !!")
    private String Password;

    private String phoneNumber;

    private String gender;

    private String DOB;

    private List<AppointmentDto> appointments;

    private Role role;

}
