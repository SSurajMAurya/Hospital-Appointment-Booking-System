package com.hospital.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hospital.main.entities.CustomUserDetails;
import com.hospital.main.entities.User;
import com.hospital.main.repository.UserRepository;
import com.hospital.main.security.JwtHelper;

@SpringBootTest
class HospitalBookingApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtHelper jwtHelper;

	@Test
	void contextLoads() {
	}

	@Test
	void testToken(){

	  User user =	userRepository.findByEmail("surajmaurya@gmail.com").get();

	  String token = jwtHelper.generateToken(user);

	  System.out.println(token);

	  System.out.println("username :" + jwtHelper.getUsernameFromToken(token));

	}

}
