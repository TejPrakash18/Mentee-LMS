package com.tej.smart_lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DSA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String title;
    private String difficulty;
    private String category;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "expected_output", columnDefinition = "TEXT")
    private String expectedOutput;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dsa_question_id")
    private List<DsaExample> examples;
}
