package com.hospital.main.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.main.dto.HospitalDto;
import com.hospital.main.dto.PageableResponse;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.Role;
import com.hospital.main.exceptions.ResourceNotFound;
import com.hospital.main.repository.HospitalRepository;
import com.hospital.main.repository.RoleRepository;
import com.hospital.main.service.HospitalService;
import com.hospital.main.utility.Helper;

@Service
public class HospitalServiceImpl implements HospitalService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public HospitalDto craeteHospital(HospitalDto hospitalDto) {

      
        
        // hospitalDto.setRole(Role.ROLE_HOSPITAL);

        // Role role = new Role();
        // role.setRoleName("ROLE_HOSPITAL");

        String password = hospitalDto.getPassword();

        Role role=  roleRepository.findByRoleName("ROLE_HOSPITAL").orElseThrow(()-> new ResourceNotFound("Role Not Found"));

       Hospital hospital = mapper.map(hospitalDto, Hospital.class);

       hospital.setPassword(passwordEncoder.encode(password));
       
       hospital.setRole(role);
       
      Hospital savedHospital = hospitalRepository.save(hospital);

      return mapper.map(savedHospital, HospitalDto.class);
        
    }

    @Override
    public HospitalDto updateHospital(HospitalDto hospitalDto ,String email) {
       
       Hospital hospital = hospitalRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("Hopspital Not Found"));

       hospital.setHospitalName(hospitalDto.getHospitalName());
       hospital.setAddress(hospitalDto.getAddress());
       hospital.setEmail(hospitalDto.getEmail());
       hospital.setDescription(hospitalDto.getDescription());
       hospital.setPhoneNumber(hospitalDto.getPhoneNumber());
       hospital.setPassword(hospitalDto.getPassword());
    
      Hospital updatedHospital = hospitalRepository.save(hospital);
      return mapper.map(updatedHospital, HospitalDto.class);
    }

    @Override
    public void deleteHospital(String email) {
        
      Hospital hospital =  hospitalRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("Hospital Does Not Exist"));
      hospitalRepository.delete(hospital);
    }

    @Override
    public PageableResponse<HospitalDto> getAllHospital(int pageNumber, int pageSize, String sortBy, String sortDir) {
        

      List<String> validSortFields = Arrays.asList("hospitalName", "address", "email", "phoneNumber"); // Add all valid fields from the Hospital entity

    // Check if the sortBy field is valid
        if (!validSortFields.contains(sortBy)) {
         throw new IllegalArgumentException("Invalid sorting field: " + sortBy);
    }

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

         Page<Hospital> page = hospitalRepository.findAll(pageable);

          PageableResponse<HospitalDto> response = Helper.getPageableResponse(page, HospitalDto.class);

        return response;
    }

    @Override
    public HospitalDto findHospitalByName(String hospitalName) {
      Hospital hospital =  hospitalRepository.findByHospitalName(hospitalName).orElseThrow(()-> new ResourceNotFound("Hospital Not Found"));

      return mapper.map(hospital, HospitalDto.class);
    }

    @Override
    public HospitalDto findHospitalByEmail(String email) {
        Hospital hospital =  hospitalRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("Hospital Not Found"));

      return mapper.map(hospital, HospitalDto.class);
    }

    @Override
    public List<HospitalDto> findHospitalByKeyword(String keyword) {
       
       List<Hospital> hospitals = hospitalRepository.findByHospitalNameContaining(keyword).orElseThrow(()-> new ResourceNotFound("No Result with" + keyword));
       List<HospitalDto> hospitalDtoList = hospitals.stream().map(hospital -> mapper.map(hospitals, HospitalDto.class)).collect(Collectors.toList());
       return hospitalDtoList;
        //  HospitalDto hospitalDtos = mapper.map(hospitals, HospitalDto.class);

        //  List<HospitalDto> hospitalDtoList = hospitals.stream().map(hospital -> hospitalDtos).collect(Collectors.toList());
        //  return hospitalDtoList;
    }

   
    
    
 

}
