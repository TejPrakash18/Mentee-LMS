package com.tej.smart_lms.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionContentDto {
    @JsonProperty("contentType")     // for outgoing JSON
    @JsonAlias("type")  // accept both type and contentType as input
    private String type;
    private String language;
    private String text;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String code;
    private String code1;
    private String code2;
    private String url;
    private String alt;
}
