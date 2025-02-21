package com.hospital.main.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.hospital.main.dto.AdminDto;
import com.hospital.main.dto.DoctorDto;
import com.hospital.main.dto.GoogleLoginRequest;
import com.hospital.main.dto.GoogleLoginUser;
import com.hospital.main.dto.HospitalDto;
import com.hospital.main.dto.JwtRequest;
import com.hospital.main.dto.JwtResponse;
import com.hospital.main.dto.UserDto;
import com.hospital.main.entities.Admin;
import com.hospital.main.entities.CustomUserDetails;
import com.hospital.main.entities.Doctor;
import com.hospital.main.entities.Hospital;
import com.hospital.main.entities.User;
import com.hospital.main.repository.DoctorRepository;
import com.hospital.main.repository.HospitalRepository;
import com.hospital.main.repository.UserRepository;
import com.hospital.main.requestHelper.CreateDoctorRequest;
import com.hospital.main.security.JwtHelper;
import com.hospital.main.service.DoctorService;
import com.hospital.main.service.HospitalService;
import com.hospital.main.service.UserService;
import com.hospital.main.service.impl.CustomUserDetailService;

import jakarta.annotation.security.PermitAll;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private JwtHelper jwtHelper;

    @Value("${google.password}")
    private String password;

    @Value("${app.google.clientId}")
    private String googleClientId;

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
        } else if (actualUser instanceof Admin) {
            response = JwtResponse.builder()
                    .token(token)
                    .admin(mapper.map((Admin) actualUser, AdminDto.class))
                    .build();
            logger.info("Admin authenticated successfully");
        }

        if (response == null) {
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



    //login with google   

    
   @PreAuthorize("permitAll()")
    @PostMapping("/login-with-google-user")
    public ResponseEntity<JwtResponse> goggleLoginHander(@RequestBody GoogleLoginRequest loginRequest)
            throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new GsonFactory())
                .setAudience(List.of(googleClientId)).build();

        GoogleIdToken googleIdToken = verifier.verify(loginRequest.getIdToken());

        if (googleIdToken != null) {

            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            String email = payload.getEmail();
            String username = payload.getSubject();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");

            logger.info("Name {}", name);
            logger.info("Email {}", email);
            logger.info("picture  {}", pictureUrl);
            logger.info("username {}", username);

            GoogleLoginUser user = new GoogleLoginUser();
            user.setEmail(email);
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);

            JwtResponse jwtResponse = null;

            User user1 = userRepository.findByEmail(email).orElse(null);
            if (user1 == null) {
                UserDto user2 = userService.createUser(mapper.map(user, UserDto.class));

                this.doAuthenticate(user.getEmail(), user.getPassword());
                User user3 = mapper.map(user2, User.class);

                String token = jwtHelper.generateToken(user3);

                jwtResponse = JwtResponse.builder().token(token).user(user2).build();

            }

            Doctor doctor = doctorRepository.findByEmail(email).orElse(null);
            if (doctor == null) {
                DoctorDto doctor1 = doctorService.createDoctor(mapper.map(user1, CreateDoctorRequest.class));

                this.doAuthenticate(user.getEmail(), user.getPassword());
                Doctor doctor2 = mapper.map(doctor1, Doctor.class);

                String token = jwtHelper.generateToken(doctor2);

                jwtResponse = JwtResponse.builder().token(token).doctor(doctor1).build();
            }

            return ResponseEntity.ok(jwtResponse);

        }

        else {
            throw new RuntimeException("Invalid token");
        }

    }

    @PreAuthorize("permitAll()")
    @PostMapping("/google-login-hospital")
    public ResponseEntity<JwtResponse> goggleLoginHospitalHandler(@RequestBody GoogleLoginRequest loginRequest)
            throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new GsonFactory())
                .setAudience(List.of(googleClientId)).build();

        GoogleIdToken googleIdToken = verifier.verify(loginRequest.getIdToken());

        if (googleIdToken != null) {

            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            String email = payload.getEmail();
            String username = payload.getSubject();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");

            logger.info("Name {}", name);
            logger.info("Email {}", email);
            logger.info("picture  {}", pictureUrl);
            logger.info("username {}", username);

            GoogleLoginUser user = new GoogleLoginUser();
            user.setEmail(email);
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);
            JwtResponse jwtResponse = null;

            Hospital hospital = hospitalRepository.findByEmail(email).orElse(null);
            if (hospital == null) {
                HospitalDto hospital1 = hospitalService.craeteHospital(mapper.map(user, HospitalDto.class));

                this.doAuthenticate(user.getEmail(), user.getPassword());
                Hospital hospital2 = mapper.map(hospital1, Hospital.class);

                String token = jwtHelper.generateToken(hospital2);

                jwtResponse = JwtResponse.builder().token(token).hospital(hospital1).build();

            }

            return ResponseEntity.ok(jwtResponse);
        }

        else {
            throw new RuntimeException("Invalid token");
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/google-login-doctor")
    public ResponseEntity<JwtResponse> googlelohinDoctor(@RequestBody GoogleLoginRequest loginRequest) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new ApacheHttpTransport(), new GsonFactory())
                .setAudience(List.of(googleClientId)).build();

        GoogleIdToken googleIdToken = verifier.verify(loginRequest.getIdToken());

        if (googleIdToken != null) {

            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            String email = payload.getEmail();
            String username = payload.getSubject();
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");

            logger.info("Name {}", name);
            logger.info("Email {}", email);
            logger.info("picture  {}", pictureUrl);
            logger.info("username {}", username);

            GoogleLoginUser user = new GoogleLoginUser();
            user.setEmail(email);
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);

            JwtResponse jwtResponse = null;
            Doctor doctor = doctorRepository.findByEmail(email).orElse(null);
            if (doctor == null) {
                DoctorDto doctor1 = doctorService.createDoctor(mapper.map(user, CreateDoctorRequest.class));

                this.doAuthenticate(user.getEmail(), user.getPassword());
                Doctor doctor2 = mapper.map(doctor1, Doctor.class);

                String token = jwtHelper.generateToken(doctor2);

                jwtResponse = JwtResponse.builder().token(token).doctor(doctor1).build();
            }

            return ResponseEntity.ok(jwtResponse);
        }
        else {
            throw new RuntimeException("Invalid token");
        }
    }

}
