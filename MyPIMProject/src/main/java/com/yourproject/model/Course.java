package com.yourproject.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 100)
    private String instructor;

    @Column(nullable = false, length = 50)
    private String difficultyLevel;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreationTimestamp // Automatically sets the timestamp on creation
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "download_count", nullable = false)
    private Integer downloadCount = 0; // Initialize to 0

    // Add status field if missing (required based on your error)
    @Column(nullable = false, length = 20)
    private String status;
}