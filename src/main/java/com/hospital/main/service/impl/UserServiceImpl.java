package com.hospital.main.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.main.dto.PageableResponse;
import com.hospital.main.dto.UserDto;
import com.hospital.main.entities.Role;
import com.hospital.main.entities.User;
import com.hospital.main.exceptions.ResourceNotFound;
import com.hospital.main.repository.RoleRepository;
import com.hospital.main.repository.UserRepository;
import com.hospital.main.service.UserService;
import com.hospital.main.utility.Helper;

import org.springframework.data.domain.Pageable;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        
        Long id = UUID.randomUUID().getMostSignificantBits();
        userDto.setId(id);
        String password = passwordEncoder.encode(userDto.getPassword());

        User user =  mapper.map(userDto, User.class);

        Role patientRole=  roleRepository.findByRoleName("ROLE_PATIENT").orElseThrow(()-> new ResourceNotFound("Role Not Found"));
        user.setRole(patientRole);

        user.setPassword(password);

        User savedUser = userRepository.save(user);

        return mapper.map(savedUser, UserDto.class);


        

    }

    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        
       User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("User Not Found"));

       user.setName(userDto.getName());
       user.setEmail(userDto.getEmail());
       user.setAddress(userDto.getAddress());
       user.setPhoneNumber(userDto.getPhoneNumber());
       user.setGender(userDto.getGender());
       user.setPassword(userDto.getPassword());
       user.setDOB(userDto.getDOB());

        User updatedUser =  userRepository.save(user);

       return mapper.map(updatedUser, UserDto.class);

    }

    @Override
    public UserDto getUserByEmail(String email) {
        
      User user =  userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("User Not Found"));

      return mapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(String email) {
        
     User user =  userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFound("User Not Found"));
       
       userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> page = userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        return response;


    }

   

}
