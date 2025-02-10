package com.hospital.main.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.main.dto.AdminDto;
import com.hospital.main.dto.DoctorDto;
import com.hospital.main.dto.HospitalDto;
import com.hospital.main.dto.JwtRequest;
import com.hospital.main.dto.JwtResponse;
import com.hospital.main.dto.UserDto;
import com.hospital.main.entities.Admin;
import com.hospital.main.entities.CustomUserDetails;
import com.hospital.main.entities.Doctor;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.User;
import com.hospital.main.security.JwtHelper;
import com.hospital.main.service.impl.CustomUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private ModelMapper mapper;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        

        logger.info("Username {}", request.getUsername());
        logger.info("password {}", request.getPassword());

        this.doAuthenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getUsername());

        Object actualUser = ((CustomUserDetails) userDetails).getActualUser();

        String token = helper.generateToken(userDetails);
        logger.info("token {}", token);

        JwtResponse response = null;

        if (actualUser instanceof User) {
            response = JwtResponse.builder()
                                  .token(token)
                                  .user(mapper.map((User) actualUser, UserDto.class))
                                  .build();
            logger.info("User authenticated successfully");
        } else if (actualUser instanceof Doctor) {
            response = JwtResponse.builder()
                                  .token(token)
                                  .doctor(mapper.map((Doctor) actualUser, DoctorDto.class))
                                  .build();
            logger.info("Doctor authenticated successfully");
        } else if (actualUser instanceof Hospital) {
            response = JwtResponse.builder()
                                  .token(token)
                                  .hospital(mapper.map((Hospital) actualUser, HospitalDto.class))
                                  .build();
            logger.info("Hospital authenticated successfully");
        }else if (actualUser instanceof Admin) {
            response = JwtResponse.builder()
                                  .token(token)
                                  .admin(mapper.map((Admin) actualUser, AdminDto.class))
                                  .build();
            logger.info("Admin authenticated successfully");
        }

        if(response==null){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(response);
    }

    private void doAuthenticate(String username, String password) {

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authentication);
            // CustomUserDetails userDetails =
            // (CustomUserDetails)authentication.getPrincipal();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
