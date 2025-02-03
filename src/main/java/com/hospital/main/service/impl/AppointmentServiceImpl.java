package com.hospital.main.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.main.dto.AppointmentDto;
import com.hospital.main.entities.Appointment;
import com.hospital.main.entities.Doctor;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.User;
import com.hospital.main.exceptions.ResourceNotFound;
import com.hospital.main.repository.AppointmentRepository;
import com.hospital.main.repository.DoctorRepository;
import com.hospital.main.repository.HospitalRepository;
import com.hospital.main.repository.UserRepository;
import com.hospital.main.requestHelper.AppointmentRequest;
import com.hospital.main.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public AppointmentDto createAppointment(AppointmentRequest appointmentRequest) {

        User user = userRepository.findByEmail(appointmentRequest.getPatientEmail()).orElseThrow(
                () -> new ResourceNotFound("User Not Found with :" + appointmentRequest.getPatientEmail()));

        Hospital hospital = hospitalRepository.findById(appointmentRequest.getHospitalId()).orElseThrow(
                () -> new ResourceNotFound("Hospital Not Found with :" + appointmentRequest.getHospitalId()));

        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFound("Doctor Not Found with :" + appointmentRequest.getDoctorId()));

        Appointment appointment = new Appointment();
        appointment.setAppointmentDataAndTime(appointmentRequest.getAppointmentDataAndTime());
        appointment.setDiseaseDescription(appointmentRequest.getDiseaseDescription());
        appointment.setDoctor(doctor);
        appointment.setHospital(hospital);
        appointment.setPatient(user);
        appointment.setIsBooked(appointmentRequest.getIsBooked());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapper.map(savedAppointment, AppointmentDto.class);
    }

    @Override
    @Transactional
    public AppointmentDto updateAppointment(AppointmentRequest appointmentRequest, Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("No Appointmenet with id :" + id));

        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFound("Doctor Not Found with :" + appointmentRequest.getDoctorId()));

        appointment.setAppointmentDataAndTime(appointmentRequest.getAppointmentDataAndTime());
        appointment.setDoctor(doctor);
        appointment.setDiseaseDescription(appointmentRequest.getDiseaseDescription());
        appointment.setIsBooked(appointmentRequest.getIsBooked());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return mapper.map(updatedAppointment, AppointmentDto.class);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("No Appointmenet with id :" + id));

        appointmentRepository.delete(appointment);
    }

    @Override
    public List<AppointmentDto> getAllAppointment() {
        
        List<Appointment> appointments = appointmentRepository.findAll();

       List<AppointmentDto> dtoList = appointments.stream().map(appointment -> mapper.map(appointment, AppointmentDto.class)).collect(Collectors.toList());

       return dtoList;
    }

}
