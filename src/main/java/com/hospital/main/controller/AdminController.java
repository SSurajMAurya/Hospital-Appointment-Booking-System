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

import com.hospital.main.dto.AdminDto;
import com.hospital.main.dto.PageableResponse;
import com.hospital.main.service.AdminService;
import com.hospital.main.utility.ApiResponseMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //create admin

    @PostMapping
    ResponseEntity<AdminDto> createAdminHandler(@Valid @RequestBody AdminDto adminDto){

        return new ResponseEntity<>(adminService.createAdmin(adminDto) , HttpStatus.CREATED);
    }

    //update

    @PutMapping
    ResponseEntity<AdminDto> updateAdminHandler(@Valid @RequestBody AdminDto adminDto , String email){

        return new ResponseEntity<>(adminService.updateAdmin(adminDto, email) , HttpStatus.OK);
    }

    //get all 
    @GetMapping
     public ResponseEntity<PageableResponse<AdminDto>> getAllAdminHandler(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(adminService.getAllAdmin(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    //get by email

    @GetMapping("/{email}")
    ResponseEntity<AdminDto> getAdminByEmailHandler(@PathVariable String email){

        return ResponseEntity.ok(adminService.getAdminByEmail(email));
    }

    //delete

    @DeleteMapping("/{email}")
    ResponseEntity<ApiResponseMessage> deleteAdminHandler(@PathVariable String email){
        adminService.deleteAdmin(email);

      ApiResponseMessage response =  ApiResponseMessage.builder().message("Admin deleted ").success(true).build();
      return ResponseEntity.ok(response);
    }

}
