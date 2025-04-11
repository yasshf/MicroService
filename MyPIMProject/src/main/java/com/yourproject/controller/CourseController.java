package com.yourproject.controller;

import com.yourproject.model.Course;
import com.yourproject.service.CourseService;
import com.yourproject.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/courses")
@Validated
public class CourseController {

    private final CourseService courseService;
    private final EmailService emailService;

    @Autowired
    public CourseController(CourseService courseService, EmailService emailService) {
        this.courseService = courseService;
        this.emailService = emailService;
    }

    @PostMapping("/by-category/{categoryId}")
    public ResponseEntity<Course> createCourseWithCategory(
            @PathVariable @NotNull Long categoryId,
            @RequestBody @Valid Course course) {
        Course createdCourse = courseService.createCourseWithCategory(categoryId, course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("/by-category/{categoryId}/{courseId}")
    public ResponseEntity<Course> updateCourseWithCategory(
            @PathVariable @NotNull Long categoryId,
            @PathVariable @NotNull Long courseId,
            @RequestBody @Valid Course course) {
        Course updatedCourse = courseService.updateCourseWithCategory(categoryId, courseId, course);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/by-category/{categoryId}/{courseId}")
    public ResponseEntity<Void> deleteCourseWithCategory(
            @PathVariable @NotNull Long categoryId,
            @PathVariable @NotNull Long courseId) {
        courseService.deleteCourseWithCategory(categoryId, courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable @NotNull Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<Course>> getCoursesByCategoryId(@PathVariable @NotNull Long categoryId) {
        List<Course> courses = courseService.getCoursesByCategoryId(categoryId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/by-category/{categoryId}/{courseId}/send-email")
    public ResponseEntity<String> sendCourseEmail(
            @PathVariable @NotNull Long categoryId,
            @PathVariable @NotNull Long courseId,
            @RequestParam String email) {
        try {
            emailService.sendCourseEmail(categoryId, courseId, email);
            return ResponseEntity.ok("Email sent successfully to " + email);
        } catch (MessagingException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }


}