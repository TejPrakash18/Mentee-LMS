package com.tej.smart_lms.services;

import com.tej.smart_lms.dto.BlogDto;
import com.tej.smart_lms.dto.BlogSectionDto;
import com.tej.smart_lms.dto.SectionContentDto;
import com.tej.smart_lms.model.Blog;
import com.tej.smart_lms.model.BlogSection;
import com.tej.smart_lms.model.SectionContent;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.BlogRepository;
import com.tej.smart_lms.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initVersionFix() {
        blogRepository.patchNullVersions();
    }

    // --------- Public DTO-based methods ---------

    public List<BlogDto> getAllBlogsDto() {
        return blogRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BlogDto getBlogDtoById(Long id) {
        Blog blog = getBlogById(id);
        return mapToDto(blog);
    }

    public List<BlogDto> getBlogsDtoByCategory(String category) {
        return blogRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BlogDto> getBlogsDtoByDifficulty(String difficulty) {
        return blogRepository.findByDifficultyIgnoreCase(difficulty)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BlogDto createOrUpdateBlog(BlogDto dto) {
        Blog blog;

        if (dto.getId() != null && blogRepository.existsById(dto.getId())) {
            blog = blogRepository.findById(dto.getId()).orElseThrow();
        } else {
            blog = new Blog();
        }

        blog.setTitle(dto.getTitle());
        blog.setCategory(dto.getCategory());
        blog.setDifficulty(dto.getDifficulty());
        blog.setDescription(dto.getDescription());
        blog.setTags(dto.getTags());
        blog.setTechnologies(dto.getTechnologies());

        List<BlogSection> mappedSections = dto.getSections() != null
                ? dto.getSections().stream().map(this::mapToSection).collect(Collectors.toList())
                : new ArrayList<>();

        if (blog.getSections() != null) {
            blog.getSections().clear();
            blog.getSections().addAll(mappedSections);
        } else {
            blog.setSections(mappedSections);
        }

        Blog saved = blogRepository.save(blog);
        return mapToDto(saved);
    }

    // --------- Completion Tracking ---------

    public void markBlogCompleted(String username, String blogTitle) {
        if (username == null || blogTitle == null) {
            throw new IllegalArgumentException("Username and blog title must not be null");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<String> completed = Optional.ofNullable(user.getCompletedBlogs()).orElse(new HashSet<>());
        completed.add(blogTitle);
        user.setCompletedBlogs(completed);
        userRepository.save(user);
    }

    public int getCompletedBlogCount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return Optional.ofNullable(user.getCompletedBlogs()).map(Set::size).orElse(0);
    }

    public List<String> getCompletedBlogs(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new ArrayList<>(Optional.ofNullable(user.getCompletedBlogs()).orElse(Collections.emptySet()));
    }

    public Map<String, Long> getCompletedBlogCountByCategory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<String> completedTitles = user.getCompletedBlogs();
        if (completedTitles == null || completedTitles.isEmpty()) return Collections.emptyMap();

        return blogRepository.findAll().stream()
                .filter(blog -> completedTitles.contains(blog.getTitle()))
                .collect(Collectors.groupingBy(
                        blog -> blog.getCategory().toLowerCase().replaceAll("\\s+", ""),
                        Collectors.counting()
                ));
    }

    // --------- Other ---------

    public long getTotalBlogCount() {
        return blogRepository.count();
    }

    public Map<String, Long> getBlogCountByCategory() {
        return blogRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        blog -> blog.getCategory().toLowerCase(),
                        Collectors.counting()
                ));
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Blog not found"));
    }

    // --------- Mapping Methods ---------

    private BlogDto mapToDto(Blog blog) {
        BlogDto dto = new BlogDto();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setCategory(blog.getCategory());
        dto.setDifficulty(blog.getDifficulty());
        dto.setDescription(blog.getDescription());
        dto.setTags(blog.getTags());
        dto.setTechnologies(blog.getTechnologies());

        dto.setSections(blog.getSections().stream()
                .map(section -> {
                    BlogSectionDto sd = new BlogSectionDto();
                    sd.setTitle(section.getTitle());
                    sd.setContent(section.getContent().stream()
                            .map(content -> new SectionContentDto(
                                    content.getType(),
                                    content.getLanguage(),
                                    content.getText(),
                                    content.getText1(),
                                    content.getText2(),
                                    content.getText3(),
                                    content.getText4(),
                                    content.getText5(),
                                    content.getCode(),
                                    content.getCode1(),
                                    content.getCode2(),
                                    content.getUrl(),
                                    content.getAlt()
                            ))
                            .collect(Collectors.toList()));
                    return sd;
                })
                .collect(Collectors.toList()));

        return dto;
    }

    private BlogSection mapToSection(BlogSectionDto dto) {
        BlogSection section = new BlogSection();
        section.setTitle(dto.getTitle());
        section.setContent(dto.getContent().stream()
                .map(this::mapToContent)
                .collect(Collectors.toList()));
        return section;
    }

    private SectionContent mapToContent(SectionContentDto dto) {
        SectionContent content = new SectionContent();
        content.setType(dto.getType());
        content.setLanguage(dto.getLanguage());
        content.setText(dto.getText());
        content.setText1(dto.getText1());
        content.setText2(dto.getText2());
        content.setText3(dto.getText3());
        content.setText4(dto.getText4());
        content.setText5(dto.getText5());
        content.setCode(dto.getCode());
        content.setCode1(dto.getCode1());
        content.setCode2(dto.getCode2());
        content.setUrl(dto.getUrl());
        content.setAlt(dto.getAlt());
        return content;
    }
}
