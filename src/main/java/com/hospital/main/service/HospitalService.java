package com.hospital.main.service;


import java.util.List;

import com.hospital.main.dto.HospitalDto;
import com.hospital.main.dto.PageableResponse;

public interface HospitalService {

    //create hospital 

    HospitalDto craeteHospital(HospitalDto hospitalDto);


    //update hospital

    HospitalDto updateHospital(HospitalDto hospitalDto , String email);


    //delete hospital 

    void deleteHospital(String email);


    //find all hospital

   PageableResponse<HospitalDto> getAllHospital(int pageNumber, int pageSize, String sortBy, String sortDir);


    //find hospital by name 

    HospitalDto findHospitalByName(String hospitalName);


    //find hospital by email

    HospitalDto findHospitalByEmail(String email);

    //find hospital by keyword

    List<HospitalDto> findHospitalByKeyword(String keyword);



}
