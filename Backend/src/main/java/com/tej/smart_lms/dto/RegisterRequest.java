package com.tej.smart_lms.dto;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
}
