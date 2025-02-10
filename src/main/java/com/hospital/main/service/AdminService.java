package com.hospital.main.service;

import com.hospital.main.dto.AdminDto;
import com.hospital.main.dto.PageableResponse;

public interface AdminService {

    //crate Admin

    AdminDto createAdmin(AdminDto adminDto);

    //update Admin

    AdminDto updateAdmin(AdminDto adminDto , String email);

    // getAll Admin

     PageableResponse<AdminDto> getAllAdmin(int pageNumber, int pageSize, String sortBy, String sortDir);

    // get Admin by email

    AdminDto getAdminByEmail(String email);

    //delete Admin

    void deleteAdmin(String email);


}
