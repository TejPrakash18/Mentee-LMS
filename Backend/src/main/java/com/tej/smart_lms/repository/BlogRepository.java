package com.tej.smart_lms.repository;

import com.tej.smart_lms.model.Blog;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByCategoryIgnoreCase(String category);
    List<Blog> findByDifficultyIgnoreCase(String difficulty);
    boolean existsByTitle(String title);
    @Transactional
    @Modifying
    @Query("UPDATE Blog b SET b.version = 0 WHERE b.version IS NULL")
    int patchNullVersions(); // return number of rows updated


}
