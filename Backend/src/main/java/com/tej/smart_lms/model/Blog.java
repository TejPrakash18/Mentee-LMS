package com.tej.smart_lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String title;

    private String difficulty; // optional

    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_id"))
    private List<String> tags;



    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "blog_technologies", joinColumns = @JoinColumn(name = "blog_id"))
    private List<String> technologies;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_id") // links to BlogSection.blog_id
    private List<BlogSection> sections;
}
