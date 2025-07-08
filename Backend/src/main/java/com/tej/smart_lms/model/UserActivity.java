package com.tej.smart_lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDate date;

    @Column(name = "questions_solved", nullable = false)
    private int count = 0;
}

