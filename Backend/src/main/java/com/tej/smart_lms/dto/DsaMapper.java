package com.tej.smart_lms.dto;

import com.tej.smart_lms.model.DSA;
import com.tej.smart_lms.model.DsaExample;

import java.util.List;
import java.util.stream.Collectors;

public class DsaMapper {

    public static DSA toEntity(DSADto dto) {
        List<DsaExample> examples = dto.getExamples().stream()
                .map(e -> new DsaExample(null, e.getInput(), e.getOutput()))
                .collect(Collectors.toList());

        DSA dsa = new DSA();
        dsa.setTitle(dto.getTitle());
        dsa.setDifficulty(dto.getDifficulty());
        dsa.setCategory(dto.getCategory());
        dsa.setExplanation(dto.getExplanation());
        dsa.setExpectedOutput(dto.getExpectedOutput());
        dsa.setExamples(examples);
        return dsa;
    }

    public static DSADto toDto(DSA entity) {
        return new DSADto(
                entity.getId(),
                entity.getTitle(),
                entity.getDifficulty(),
                entity.getCategory(),
                entity.getExplanation(),
                entity.getExpectedOutput(),
                entity.getExamples().stream()
                        .map(e -> new ExampleDto(e.getInput(), e.getOutput()))
                        .collect(Collectors.toList()),
                entity.getVersion() // ✅ include version
        );
    }

}
