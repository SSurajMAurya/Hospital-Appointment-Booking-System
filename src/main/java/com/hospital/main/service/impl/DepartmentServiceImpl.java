package com.hospital.main.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.main.dto.DepartmentsDto;
import com.hospital.main.entities.Departments;
import com.hospital.main.exceptions.ResourceNotFound;
import com.hospital.main.repository.DepartmentRepository;
import com.hospital.main.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentsDto createDepartment(DepartmentsDto departmentsDto) {
        
       Departments departments = mapper.map(departmentsDto, Departments.class);

      return mapper.map(departmentRepository.save(departments), DepartmentsDto.class);

    }

    @Override
    public DepartmentsDto updateDepartment(DepartmentsDto departmentsDto, Long id) {
        
      Departments departments =  departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFound("No Department is there with id :"+ id));

      departments.setDepartmentName(departmentsDto.getDepartmentName());
      departments.setDescriptions(departmentsDto.getDescriptions());

      return mapper.map(departmentRepository.save(departments), DepartmentsDto.class);
    }

    @Override
    public void deleteDepartment(Long id) {
        
        Departments departments =  departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFound("No Department is there with id :"+ id));

        departmentRepository.delete(departments);

    }

    @Override
    public DepartmentsDto getDepartment(Long id) {

       Departments departments =  departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFound("No Department is there with id :"+ id));
       
       
       return mapper.map(departments, DepartmentsDto.class);

    }

    @Override
    public List<DepartmentsDto> getAllDepartment() {
       
        List<Departments> departments = departmentRepository.findAll();

        List<DepartmentsDto> departmentsDtosList = departments.stream()
                                                    .map(department -> mapper.map(department, DepartmentsDto.class))
                                                    .collect(Collectors.toList());

        return departmentsDtosList;
    }

}
