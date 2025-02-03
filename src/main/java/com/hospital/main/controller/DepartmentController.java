package com.hospital.main.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.hospital.main.dto.DepartmentsDto;
import com.hospital.main.service.DepartmentService;
import com.hospital.main.utility.ApiResponseMessage;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @PostMapping
    public ResponseEntity<DepartmentsDto> createDepartmentHandler(@RequestBody DepartmentsDto departmentsDto){

       return new ResponseEntity<>(departmentService.createDepartment(departmentsDto) ,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentsDto> updateDepartmentHandler(@RequestBody DepartmentsDto departmentsDto , @PathVariable Long id){

        return ResponseEntity.ok(departmentService.updateDepartment(departmentsDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseMessage> deleteDepartmentHandler(@PathVariable Long id){

        departmentService.deleteDepartment(id);

        ApiResponseMessage response =ApiResponseMessage.builder().message("Department deleted successfully").success(true).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsDto> getByDepartmentId( @PathVariable Long id){

       return ResponseEntity.ok(departmentService.getDepartment(id)); 
    }

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentsDto>> getAllDepartments(){

        return ResponseEntity.ok(departmentService.getAllDepartment());
    }

}
