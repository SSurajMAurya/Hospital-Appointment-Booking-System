package com.hospital.main.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.main.dto.DoctorDto;
import com.hospital.main.dto.PageableResponse;
import com.hospital.main.entities.Departments;
import com.hospital.main.entities.Doctor;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.Role;
import com.hospital.main.exceptions.ResourceNotFound;
import com.hospital.main.repository.DepartmentRepository;
import com.hospital.main.repository.DoctorRepository;
import com.hospital.main.repository.HospitalRepository;
import com.hospital.main.repository.RoleRepository;
import com.hospital.main.requestHelper.CreateDoctorRequest;
import com.hospital.main.service.DoctorService;
import com.hospital.main.utility.Helper;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public DoctorDto createDoctor(CreateDoctorRequest doctorDto) {

        String hospitalEmail = doctorDto.getHospitalEmail();
        Long departmentId = doctorDto.getDepartmentId();

        String password = doctorDto.getPassword();

        // Role role = new Role();
        // role.setRoleName("ROLE_DOCTOR");


        Hospital hospital =  hospitalRepository.findByEmail(hospitalEmail).orElseThrow(()-> new ResourceNotFound("Hospital Does Not Exist"));
        Departments departments= departmentRepository.findById(departmentId).orElseThrow(()-> new ResourceNotFound("Department Not Found"));

        Role role=  roleRepository.findByRoleName("ROLE_DOCTOR").orElseThrow(()-> new ResourceNotFound("Role Not Found"));
        // doctorDto.setRole(ROLE.ROLE_DOCTOR);


        // Doctor doctor =mapper.map(doctorDto, Doctor.class);
        // doctor.setHospital(hospital);
        // doctor.setDepartment(departments);

        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setHospital(hospital);
        // doctor.setRole(Role.ROLE_DOCTOR);
        doctor.setRole(role);
        doctor.setPassword(passwordEncoder.encode(password));

        doctor.setDepartment(departments);
       
        Doctor savedDoctor =doctorRepository.save(doctor);

        return mapper.map(savedDoctor, DoctorDto.class);
     
    
    }

    @Override
    public DoctorDto updateDoctor(DoctorDto doctorDto , String email) {
        
       Doctor doctor = doctorRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("Doctor does not exits with email :"+ email));

       doctor.setName(doctorDto.getName());
       doctor.setEmail(doctorDto.getEmail());
       doctor.setSpecialization(doctorDto.getSpecialization());
       doctor.setPhoneNumber(doctorDto.getPhoneNumber());
    //    doctor.setDepartment(doctorDto.getDepartment());

    // DoctorRepository.findByEmail
    //    doctor.setHospital(doctorDto.getHospital());

        Doctor updatedDoctor =doctorRepository.save(doctor);
        return mapper.map(updatedDoctor, DoctorDto.class);
       
        
        
    }

    @Override
    public PageableResponse<DoctorDto> getAllDoctors(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

         Page<Doctor> page = doctorRepository.findAll(pageable);

         PageableResponse<DoctorDto> response = Helper.getPageableResponse(page, DoctorDto.class);

        return response;
    }

    @Override
    public DoctorDto getDoctorByEmail(String email) {
       Doctor doctor = doctorRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("Doctor does not exits with email :"+email));

       return mapper.map(doctor, DoctorDto.class);
    }

    @Override
    public DoctorDto getDoctorByName(String name) {
        Doctor doctor = doctorRepository.findByName(name).orElseThrow(()-> new ResourceNotFound("No Doctor Found with Name :"+name));

       return mapper.map(doctor, DoctorDto.class);
    }

    @Override
    public void deleteDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("Doctor does not exits with email :"+email));

        doctorRepository.delete(doctor);
    }

}
