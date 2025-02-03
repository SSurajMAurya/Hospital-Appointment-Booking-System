package com.hospital.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.main.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor ,Long> {

    Optional<Doctor> findByName(String name);

    Optional<Doctor> findByEmail(String email);

    // Optional<Doctor> findByDepartment(String department);

    Optional<Doctor> findBySpecialization(String specialization);


}
