package com.hospital.main.controller;

import java.util.List;

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

import com.hospital.main.dto.HospitalDto;
import com.hospital.main.dto.PageableResponse;
import com.hospital.main.service.HospitalService;
import com.hospital.main.utility.ApiResponseMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    //create hospital api

    @PostMapping
    public ResponseEntity<HospitalDto> createHopsitalHandler( @RequestBody HospitalDto hospitalDto){

       HospitalDto createdHospital = hospitalService.craeteHospital(hospitalDto);

       return new ResponseEntity<>(createdHospital ,HttpStatus.CREATED);

    }

    //update hospital api

    @PutMapping("/{email}")
    public ResponseEntity<HospitalDto> upadteHospitalHandler(@RequestBody HospitalDto hospitalDto ,@PathVariable String email){

      HospitalDto upadatedHospital =  hospitalService.updateHospital(hospitalDto, email);

      return ResponseEntity.ok(upadatedHospital);
    }

    //delete hospital api

    @DeleteMapping("/{email}")
    public ResponseEntity<ApiResponseMessage> deleteHospitalHandler(@PathVariable String email){

        hospitalService.deleteHospital(email);

        ApiResponseMessage response = ApiResponseMessage.builder().message("Hospital deleted successfully")
        .success(true).build();

        return ResponseEntity.ok(response);
    }

    //get all hospital api 

    @GetMapping
    @PreAuthorize("hasRole('HOSPITAL')" + "hasRole('ADMIN')")
    public ResponseEntity<PageableResponse<HospitalDto>> getAllHospital(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "hospitalName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(hospitalService.getAllHospital(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    //get hospital by name api

    @GetMapping("/{hospitalName}")
    public ResponseEntity<HospitalDto> getHospitalByNameHandler(@PathVariable String hospitalName){

    HospitalDto hospital = hospitalService.findHospitalByName(hospitalName);

    return new ResponseEntity<>(hospital ,HttpStatus.OK);


    }

    //get hospital by email api

    @GetMapping("/findBy/{email}")
    public ResponseEntity<HospitalDto> getHospitalByEmailHandler(@PathVariable String email){

    HospitalDto hospital = hospitalService.findHospitalByEmail(email);

    return ResponseEntity.ok(hospital);


    }

    //search hospital by keyword
    @GetMapping("/serach/{keyword}")
    public ResponseEntity<List<HospitalDto>> serachByKeyword(@PathVariable String keyword){

       List<HospitalDto> hospitals = hospitalService.findHospitalByKeyword(keyword);

       return ResponseEntity.ok(hospitals);
    }


}
