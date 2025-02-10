package com.hospital.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.main.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin , Long> {

    Optional<Admin> findByEmail(String email);

}
