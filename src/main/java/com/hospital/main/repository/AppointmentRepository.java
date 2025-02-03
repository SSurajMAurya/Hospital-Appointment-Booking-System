package com.hospital.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.main.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment , Long>{

}
