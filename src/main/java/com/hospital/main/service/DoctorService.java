package com.hospital.main.service;

import com.hospital.main.dto.DoctorDto;
import com.hospital.main.dto.PageableResponse;
import com.hospital.main.requestHelper.CreateDoctorRequest;

public interface DoctorService {

    //create Doctor

    DoctorDto createDoctor(CreateDoctorRequest doctorDto);

    //update Doctor 
    DoctorDto updateDoctor(DoctorDto doctorDto , String email);

    //find all doctor

    PageableResponse<DoctorDto> getAllDoctors(int pageNumber, int pageSize, String sortBy, String sortDir);

    //find doctor by email

    DoctorDto getDoctorByEmail(String email);

    //find doctor by name 
    DoctorDto getDoctorByName(String name);

    //delete doctor

    void deleteDoctor(String email);

}
