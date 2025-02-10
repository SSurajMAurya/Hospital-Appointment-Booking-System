package com.hospital.main.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
// @NoArgsConstructor
public class CustomUserDetails implements UserDetails  {

    private final Object actualUser; // Stores User, Doctor, or Hospital

    // public CustomUserDetails(Object actualUser) {
    //     this.actualUser = actualUser;
    // }

    public Object getActualUser() {
        return actualUser;
    }

    @Override
    public String getUsername() {
        // Use getEmail() as the username for authentication
        if (actualUser instanceof User) {
            return ((User) actualUser).getEmail();
        } else if (actualUser instanceof Doctor) {
            return ((Doctor) actualUser).getEmail();
        } else if (actualUser instanceof Hospital) {
            return ((Hospital) actualUser).getEmail();
        }else if(actualUser instanceof Admin){
            return ((Admin)actualUser).getEmail();
        }
        return null;
    }

    @Override
    public String getPassword() {
        // Fetch password from the actual user entity
        if (actualUser instanceof User) {
            return ((User) actualUser).getPassword();
        } else if (actualUser instanceof Doctor) {
            return ((Doctor) actualUser).getPassword();
        } else if (actualUser instanceof Hospital) {
            return ((Hospital) actualUser).getPassword();
        }else if(actualUser instanceof Admin){
            return ((Admin)actualUser).getPassword();
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Extract role and format it as a Spring Security authority
        String role = null;
        if (actualUser instanceof User) {
            role = ((User) actualUser).getRole().getRoleName();
        } else if (actualUser instanceof Doctor) {
            role = ((Doctor) actualUser).getRole().getRoleName();
        } else if (actualUser instanceof Hospital) {
            role = ((Hospital) actualUser).getRole().getRoleName();
        }else if(actualUser instanceof Admin){
            role = ((Admin)actualUser).getRole().getRoleName();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    // Boilerplate security flags (customize as needed)
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
