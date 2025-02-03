package com.hospital.main.service;

import java.util.List;

import com.hospital.main.dto.AppointmentDto;
import com.hospital.main.requestHelper.AppointmentRequest;

public interface AppointmentService {

    //create Appointment 

    AppointmentDto createAppointment(AppointmentRequest appointmentRequest);

    //update Appointment

    AppointmentDto updateAppointment(AppointmentRequest appointmentRequest , Long id);

    //delete Appointment 

    void deleteAppointment(Long id);

    // getAll Apoointment
    List<AppointmentDto> getAllAppointment();

}
