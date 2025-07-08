package com.tej.smart_lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String college;
    private String education;
    private String location;

    private String linkedin;
    private String github;



    private String avatarUrl; // e.g., "/avatars/avatar1.png"

    @ElementCollection(fetch = FetchType.EAGER) // create the corresponding tables of the user, and reference via the username
    private Set<String> skills = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> completedBlogs;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> completedDsaQuestion = new HashSet<>();

}
