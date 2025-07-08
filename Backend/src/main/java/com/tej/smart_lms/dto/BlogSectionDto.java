package com.tej.smart_lms.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogSectionDto {
    private String title;
    private List<SectionContentDto> content;
}
