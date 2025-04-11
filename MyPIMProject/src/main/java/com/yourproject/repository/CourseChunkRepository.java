package com.yourproject.repository;

import com.yourproject.model.CourseChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CourseChunkRepository extends JpaRepository<CourseChunk, Long> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM course_chunk 
        WHERE course_id = :courseId 
        ORDER BY embedding <=> CAST(:embedding AS vector) 
        LIMIT 3
    """)
    List<CourseChunk> findRelevantChunks(
            @Param("courseId") Long courseId,
            @Param("embedding") List<Double> embedding
    );
}