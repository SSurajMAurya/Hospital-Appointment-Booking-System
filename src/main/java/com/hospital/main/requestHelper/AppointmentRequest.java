package com.hospital.main.requestHelper;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {

    private LocalDateTime appointmentDataAndTime;
    private String patientEmail;
    private Long hospitalId;
    private Long doctorId;
    private String diseaseDescription;
    private String isBooked;
   

}
