package com.tej.smart_lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DSADto {
    private Long id;
    private String title;
    private String difficulty;
    private String category;
    private String explanation;

    @JsonProperty("expected_output")
    private String expectedOutput;

    private List<ExampleDto> examples;

    private Long version;
}
