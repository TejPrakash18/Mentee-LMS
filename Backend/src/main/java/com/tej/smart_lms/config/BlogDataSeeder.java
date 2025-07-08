//    package com.tej.smart_lms.config;
//
//    import com.fasterxml.jackson.core.type.TypeReference;
//    import com.fasterxml.jackson.databind.ObjectMapper;
//    import com.tej.smart_lms.model.Blog;
//    import com.tej.smart_lms.repository.BlogRepository;
//    import org.springframework.beans.factory.annotation.Autowired;
//    import org.springframework.boot.CommandLineRunner;
//    import org.springframework.core.io.ClassPathResource;
//    import org.springframework.stereotype.Component;
//
//    import java.io.InputStream;
//    import java.util.*;
//    import java.util.stream.Collectors;
//
//    @Component
//    public class BlogDataSeeder implements CommandLineRunner {
//
//        @Autowired
//        private BlogRepository blogRepository;
//
//        @Override
//        public void run(String... args) throws Exception {
//            ObjectMapper mapper = new ObjectMapper();
//            InputStream inputStream = new ClassPathResource("data/blogs.json").getInputStream();
//
//            List<Blog> blogList = mapper.readValue(inputStream, new TypeReference<>() {});
//            Map<String, Blog> existingMap = blogRepository.findAll().stream()
//                    .collect(Collectors.toMap(Blog::getTitle, b -> b));
//
//            List<Blog> toSave = new ArrayList<>();
//
//            for (Blog blog : blogList) {
//                Blog existing = existingMap.get(blog.getTitle());
//                if (existing != null) {
//                    existing.setCategory(blog.getCategory());
//                    existing.setDifficulty(blog.getDifficulty());
//                    existing.setTags(blog.getTags());
//                    existing.setSections(blog.getSections());
//                    toSave.add(existing);
//                } else {
//                    toSave.add(blog);
//                }
//            }
//
//            blogRepository.saveAll(toSave);
//            System.out.println("✅ Blogs inserted/updated: " + toSave.size());
//        }
//    }
