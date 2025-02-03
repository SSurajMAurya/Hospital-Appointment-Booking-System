package com.hospital.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.main.entities.Departments;
import com.hospital.main.entities.Doctor;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.Role;
import com.hospital.main.entities.User;
import com.hospital.main.exceptions.ResourceNotFound;
import com.hospital.main.repository.DepartmentRepository;
import com.hospital.main.repository.DoctorRepository;
import com.hospital.main.repository.HospitalRepository;
import com.hospital.main.repository.RoleRepository;
import com.hospital.main.repository.UserRepository;

import jakarta.persistence.EntityManager;

@SpringBootApplication
public class HospitalBookingApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private RoleRepository roleRepository;


	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	public static void main(String[] args) {
		SpringApplication.run(HospitalBookingApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		// Role role = new Role();
        // role.setRoleName("ROLE_PATIENT");

		// User user = userRepository.findByEmail("surajmaurya@gmail.com").orElse(null);
		// Role patientRole=  roleRepository.findByRoleName("ROLE_PATIENT").orElseThrow(()-> new ResourceNotFound("Role Not Found"));

		// if(user==null){
			// User user = new User();

			// user.setName("Suraj");
			// user.setEmail("surajmaurya@gmail.com");
			// user.setPassword(passwordEncoder.encode("Suraj@321"));
			// user.setPhoneNumber("548764756745");
			// user.setGender("male");
			// user.setAddress("gfehvbfewhjvb");
			// user.setRole(patientRole);

			// userRepository.save(user);

		// }


		// Role role = new Role();
        // role.setRoleName("ROLE_HOSPITAL");
		// Hospital hospital =hospitalRepository.findByEmail("admin@hopewellmed@gmail.com").orElse(null);
		// Role role=  roleRepository.findByRoleName("ROLE_HOSPITAL").orElseThrow(()-> new ResourceNotFound("Role Not Found"));
		//  Role role = entityManager.merge(roleRepository.findById(4l).get());
		// if(hospital == null){
			
		

		    // Hospital hospital1 = new Hospital();

			// hospital1.setHospitalName("city hospital");
			// hospital1.setEmail("cityhospital@gmail.com");
			// hospital1.setPassword(passwordEncoder.encode("City@321"));
			// hospital1.setAddress("Ballia new Hospital Road");
			// hospital1.setDescription("A multiSpecialliaty Hospital");
			// hospital1.setRole(role);

			// hospitalRepository.save(hospital1);

			// Departments departments = new Departments();
			// departments.setDepartmentName("Cardiology");
			// departments.setDescriptions( "Department specializing in heart-related treatments");

			// departmentRepository.save(departments);

		// }

		// Departments departments =departmentRepository.findById(1l).orElse(null);
		// Hospital hospital = hospitalRepository.findById(1l).orElse(null);

		// Role role=  roleRepository.findByRoleName("ROLE_DOCTOR").orElseThrow(()-> new ResourceNotFound("Role Not Found"));
		// Doctor doctor = new Doctor();
		// doctor.setName("Rahul Dubey");
		// doctor.setEmail("rahul@gmail.com");
		// doctor.setSpecialization("Cardiologist");
		// doctor.setPhoneNumber("4756754578");
		// doctor.setPassword(passwordEncoder.encode("Doctor@321"));
		// doctor.setDepartment(departments);
		// doctor.setHospital(hospital);
		// doctor.setRole(role);
		
		// doctorRepository.save(doctor);


			

		
	}

}
