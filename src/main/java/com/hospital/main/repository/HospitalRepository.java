package com.hospital.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.main.entities.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital , Long>{

    Optional<Hospital> findByEmail(String email);

    Optional<Hospital> findByHospitalName(String hospitalname);
    
    Optional<List<Hospital>> findByHospitalNameContaining(String keyword);

}
