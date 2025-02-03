package com.hospital.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.main.dto.DoctorDto;
import com.hospital.main.dto.PageableResponse;
import com.hospital.main.requestHelper.CreateDoctorRequest;
import com.hospital.main.service.DoctorService;
import com.hospital.main.utility.ApiResponseMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping()
    public ResponseEntity<DoctorDto> createDoctorHandler(@Valid @RequestBody CreateDoctorRequest doctorDto ){

       DoctorDto doctor = doctorService.createDoctor(doctorDto);

       return new ResponseEntity<>(doctor , HttpStatus.CREATED);

    }

    @PutMapping("/{email}")
    public ResponseEntity<DoctorDto> updateDoctorHandler(@RequestBody DoctorDto doctorDto , @PathVariable String email){

       DoctorDto upadtedDoctor =   doctorService.updateDoctor(doctorDto, email);

       return ResponseEntity.ok(upadtedDoctor);

    }

     @GetMapping
    public ResponseEntity<PageableResponse<DoctorDto>> getAllHospital(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(doctorService.getAllDoctors(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    } 



    @GetMapping("/{email}")
    public ResponseEntity<DoctorDto> findDoctorByEmailHandler(@PathVariable String email){

       return ResponseEntity.ok( doctorService.getDoctorByEmail(email));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<DoctorDto> findDoctorByNameHandler(@PathVariable String name){

       return ResponseEntity.ok( doctorService.getDoctorByName(name));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponseMessage> deleteDoctor(@PathVariable String email){

        doctorService.deleteDoctor(email);

        return ResponseEntity.ok(ApiResponseMessage.builder().message("Doctor deleted successfully")
                                                              .success(true).build());
    }



}
