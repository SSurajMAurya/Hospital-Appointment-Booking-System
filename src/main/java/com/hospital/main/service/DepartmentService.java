package com.hospital.main.service;

import java.util.List;

import com.hospital.main.dto.DepartmentsDto;

public interface DepartmentService {

    //create Department
     DepartmentsDto createDepartment(DepartmentsDto departmentsDto);

    //update department

     DepartmentsDto updateDepartment(DepartmentsDto departmentsDto ,Long id );

    //delete department

     void deleteDepartment(Long id);

    //getDepartment

     DepartmentsDto getDepartment(Long id);

     //get all departments

     List<DepartmentsDto> getAllDepartment();

}
