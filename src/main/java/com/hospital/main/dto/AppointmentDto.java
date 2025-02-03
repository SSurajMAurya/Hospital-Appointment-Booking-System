package com.hospital.main.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {

     private Long id;

    private LocalDateTime appointmentDataAndTime;

    private Long patientId;

    private Long hospitalId;

    private Long doctorId;

    private String diseaseDescription;

    private String isBooked;


}
