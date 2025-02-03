package com.hospital.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.main.entities.User;

public interface UserRepository extends JpaRepository<User , Long>{

    Optional<User> findByEmail(String email);
    


}
