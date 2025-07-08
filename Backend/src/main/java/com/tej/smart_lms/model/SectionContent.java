package com.tej.smart_lms.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String type;
    private String language;   // okay, short field
    private String url;        // usually short, okay
    private String alt;        // short text like "image alt", okay

    @Column(columnDefinition = "TEXT")
    private String text;
    @Column(columnDefinition = "TEXT")
    private String text1;
    @Column(columnDefinition = "TEXT")
    private String text2;
    @Column(columnDefinition = "TEXT")
    private String text3;

    @Column(columnDefinition = "TEXT")
    private String text4;
    @Column(columnDefinition = "TEXT")
    private String text5;
    @Column(columnDefinition = "TEXT")
    private String code;
    @Column(columnDefinition = "TEXT")
    private String code1;
    @Column(columnDefinition = "TEXT")
    private String code2;
}
