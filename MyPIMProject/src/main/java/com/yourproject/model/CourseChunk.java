package com.yourproject.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import java.util.List;

@Data
@Entity
@Table(name = "course_chunk")
public class CourseChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Type(ListArrayType.class)
    @Column(columnDefinition = "vector(768)")
    private List<Double> embedding;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}