package com.hospital.main.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.main.dto.AdminDto;
import com.hospital.main.dto.PageableResponse;
import com.hospital.main.entities.Admin;
import com.hospital.main.entities.Role;
import com.hospital.main.exceptions.ResourceNotFound;
import com.hospital.main.repository.AdminRepository;
import com.hospital.main.repository.RoleRepository;
import com.hospital.main.service.AdminService;
import com.hospital.main.utility.Helper;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
         
        Role adminRole=  roleRepository.findByRoleName("ADMIN").orElseThrow(()-> new ResourceNotFound("Role Not Found"));
      String password = adminDto.getPassword();
       Admin admin = mapper.map(adminDto, Admin.class);

       admin.setRole(adminRole);
       admin.setPassword(passwordEncoder.encode(password));
       
        Admin savedAdmin =adminRepository.save(admin);
        return mapper.map(savedAdmin, AdminDto.class);
    }

    @Override
    public AdminDto updateAdmin(AdminDto adminDto, String email) {
        
       Admin admin = adminRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("No Admin Exits with email: "+ email));
       admin.setName(adminDto.getName());
       admin.setPassword(adminDto.getPassword());
       admin.setGender(adminDto.getGender());
       admin.setPhoneNumber(adminDto.getPhoneNumber());

       Admin upadtedAdmin =adminRepository.save(admin);

       return mapper.map(upadtedAdmin, AdminDto.class);
    }

    @Override
    public PageableResponse<AdminDto> getAllAdmin(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Admin> page = adminRepository.findAll(pageable);

        PageableResponse<AdminDto> response = Helper.getPageableResponse(page, AdminDto.class);

        return response;
    }

    @Override
    public AdminDto getAdminByEmail(String email) {
       
        Admin admin = adminRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("No Admin Exits with email: "+ email));

        return mapper.map(admin, AdminDto.class);
    }

    @Override
    public void deleteAdmin(String email) {
        Admin admin = adminRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("No Admin Exits with email: "+ email));
        adminRepository.delete(admin);
    }

}
