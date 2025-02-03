package com.hospital.main.service;


import com.hospital.main.dto.PageableResponse;
import com.hospital.main.dto.UserDto;

public interface UserService {

    //craete user
    UserDto createUser(UserDto userDto);

    // update user

    UserDto updateUser(UserDto userDto , String email);

    //get User 

    UserDto getUserByEmail(String email);

    //delete User 

    void deleteUser(String email);


    //get All User

   PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


}
