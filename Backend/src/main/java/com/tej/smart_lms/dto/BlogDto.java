package com.tej.smart_lms.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private String category;
    private List<String> tags;
    private List<String> technologies;
    private List<BlogSectionDto> sections;
}
