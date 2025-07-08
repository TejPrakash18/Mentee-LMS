package com.tej.smart_lms.controller;

import com.tej.smart_lms.dto.BlogDto;
import com.tej.smart_lms.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogsDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.getBlogDtoById(id));
    }

    @GetMapping("/category")
    public ResponseEntity<List<BlogDto>> getByCategory(@RequestParam String category) {
        return ResponseEntity.ok(blogService.getBlogsDtoByCategory(category));
    }

    @GetMapping("/difficulty")
    public ResponseEntity<List<BlogDto>> getByDifficulty(@RequestParam String difficulty) {
        return ResponseEntity.ok(blogService.getBlogsDtoByDifficulty(difficulty));
    }

    @PostMapping
    public ResponseEntity<BlogDto> createOrUpdateBlog(@RequestBody BlogDto blogDto) {
        return ResponseEntity.ok(blogService.createOrUpdateBlog(blogDto));
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeBlog(@RequestBody Map<String, String> req) {
        String username = req.get("username");
        String title = req.get("title");
        blogService.markBlogCompleted(username, title);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/completed/count/category")
    public ResponseEntity<Map<String, Long>> getCompletedBlogCountByCategory(@RequestParam String username) {
        return ResponseEntity.ok(blogService.getCompletedBlogCountByCategory(username));
    }

    @GetMapping("/completed/count")
    public ResponseEntity<Integer> getCompletedBlogCount(@RequestParam String username) {
        return ResponseEntity.ok(blogService.getCompletedBlogCount(username));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<String>> getCompletedBlogs(@RequestParam String username) {
        return ResponseEntity.ok(blogService.getCompletedBlogs(username));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalBlogCount() {
        return ResponseEntity.ok(blogService.getTotalBlogCount());
    }

    @GetMapping("/count/category")
    public ResponseEntity<Map<String, Long>> getBlogCountByCategory() {
        return ResponseEntity.ok(blogService.getBlogCountByCategory());
    }
}
