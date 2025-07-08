//
//package com.tej.smart_lms.config;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tej.smart_lms.dto.DSADto;
//import com.tej.smart_lms.dto.DsaMapper;
//import com.tej.smart_lms.model.DSA;
//import com.tej.smart_lms.repository.DsaQuestionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//public class DsaDataSeeder implements CommandLineRunner {
//
//    @Autowired private DsaQuestionRepository dsaRepo;
//
//    @Override
//    public void run(String... args) throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        InputStream inputStream = new ClassPathResource("data/dsa.json").getInputStream();
//
//        List<DSADto> dtoList = mapper.readValue(inputStream, new TypeReference<>() {});
//        List<DSA> newEntities = dtoList.stream().map(DsaMapper::toEntity).toList();
//
//        // Load existing by title into map for lookup
//        Map<String, DSA> existingMap = dsaRepo.findAll().stream()
//                .collect(Collectors.toMap(DSA::getTitle, q -> q));
//
//        List<DSA> toSave = new ArrayList<>();
//
//        for (DSA newQ : newEntities) {
//            DSA existing = existingMap.get(newQ.getTitle());
//            if (existing != null) {
//                // Update existing record
//                existing.setDifficulty(newQ.getDifficulty());
//                existing.setCategory(newQ.getCategory());
//                existing.setExplanation(newQ.getExplanation());
//                existing.setExpectedOutput(newQ.getExpectedOutput());
//                existing.setExamples(newQ.getExamples());
//                toSave.add(existing);
//            } else {
//                // New entry
//                toSave.add(newQ);
//            }
//        }
//
//        dsaRepo.saveAll(toSave);
//        System.out.println("✅ DSA questions inserted/updated: " + toSave.size());
//    }
//}
