package com.hospital.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleLoginUser {

    private String username;
    private String email;
    private String name;
    private String password;

}
