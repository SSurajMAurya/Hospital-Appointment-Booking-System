package com.hospital.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.main.entities.Departments;

public interface DepartmentRepository extends JpaRepository<Departments , Long> {

    Optional<Departments> findByDepartmentName(String departmentName);

}
