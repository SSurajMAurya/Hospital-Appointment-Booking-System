package com.hospital.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.main.dto.PageableResponse;
import com.hospital.main.dto.UserDto;
import com.hospital.main.service.UserService;
import com.hospital.main.utility.ApiResponseMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "REST APIs related to User Entity")  // decription that shows on swagger
public class UserController {

    @Autowired
    public UserService userService;

    //Registering user Api
    @PostMapping
    @PreAuthorize("hasAnyRole")
    public ResponseEntity<UserDto> createUserHandler(@RequestBody UserDto userDto){

       UserDto savedUser = userService.createUser(userDto);

       return new ResponseEntity<>(savedUser ,HttpStatus.CREATED);

    }


    //updating user api
    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto ,
                                      @PathVariable("email") String email){

        UserDto updatedUser = userService.updateUser(userDto, email);

        return new ResponseEntity<>(updatedUser ,HttpStatus.OK);
    }

    //get single user api
    @GetMapping("/{email}")
    public ResponseEntity<UserDto> findUserByemailHandler(@PathVariable String email){

       UserDto user = userService.getUserByEmail(email);

       return new ResponseEntity<>(user , HttpStatus.OK);
    }

    //delete user api
    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponseMessage> deleteUserHandler(@PathVariable String email){

        userService.deleteUser(email);

       ApiResponseMessage respone = ApiResponseMessage.builder().message("User Deleted Successfully")
                                    .success(true).build();

        
       return ResponseEntity.ok(respone);
    }

    @GetMapping
    @PreAuthorize("hasRole('PATIENT')")
    @Operation(summary = "Get all users", description = "Fetch all users from the database")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


}
