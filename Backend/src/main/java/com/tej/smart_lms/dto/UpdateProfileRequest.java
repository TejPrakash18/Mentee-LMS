package com.tej.smart_lms.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateProfileRequest {
    @Column(unique = true)
    private String userName;
    private String email;
    private String college;
    private String education;
    private String location;
    private String linkedin;
    private String github;
    private Set<String> skills;

}
