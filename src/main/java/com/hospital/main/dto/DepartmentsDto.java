package com.hospital.main.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentsDto {

    private Long id;

    private String departmentName;

    private String descriptions;

    private HospitalDto hospital;

    private DoctorDto doctor;

}
