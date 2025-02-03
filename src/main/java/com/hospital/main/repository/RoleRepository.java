package com.hospital.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.main.entities.Role;

public interface RoleRepository extends JpaRepository<Role , Long>{

    Optional<Role> findByRoleName(String roleName);

}
