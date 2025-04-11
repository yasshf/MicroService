package com.yourproject.controller;

import com.yourproject.dto.QuestionRequest;
import com.yourproject.service.TutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tutor")
public class TutorController {

    private final TutorService tutorService;

    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping("/by-category/{categoryId}/{courseId}/ask")
    public ResponseEntity<?> askQuestion(
            @PathVariable Long categoryId,
            @PathVariable Long courseId,
            @RequestBody QuestionRequest request
    ) {
        try {
            String answer = tutorService.answerQuestion(categoryId, courseId, request.getQuestion());
            return ResponseEntity.ok().body(Map.of("answer", answer));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/by-category/{categoryId}/{courseId}/ask")
    public ResponseEntity<?> askQuestionGet(
            @PathVariable Long categoryId,
            @PathVariable Long courseId,
            @RequestParam String question
    ) {
        try {
            String answer = tutorService.answerQuestion(categoryId, courseId, question);
            return ResponseEntity.ok().body(Map.of("answer", answer));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
