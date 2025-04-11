package com.example.pielearning.service;

import com.example.pielearning.entite.Test;
import com.example.pielearning.repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    public Test createTest(Test test) {
        logger.info("Enregistrement du test: {}", test);
        Test savedTest = testRepository.save(test);
        logger.info("Test enregistré avec ID: {}", savedTest.getId());
        return savedTest;
    }

    public Test updateTest(Long id, Test testDetails) {
        Test test = getTestById(id);
        test.setTitre(testDetails.getTitre());
        test.setDescription(testDetails.getDescription());
        test.setDuration(testDetails.getDuration());
        test.setNiveau(testDetails.getNiveau());
        return testRepository.save(test);
    }

    public void deleteTest(Long id) {
        Test test = getTestById(id);
        testRepository.delete(test);
        logger.info("Test supprimé avec ID: {}", id);
    }
    public int calculateScore(Test test, List<String> answers) {
        // Supposons que les bonnes réponses sont stockées en base de données
        List<String> correctAnswers = List.of("reponse1", "reponse2", "reponse3", "reponse4"); // Remplace par un vrai stockage

        int totalQuestions = correctAnswers.size();
        int scorePerQuestion = 100 / totalQuestions; // Score dynamique

        int score = 0;
        for (int i = 0; i < Math.min(correctAnswers.size(), answers.size()); i++) {
            if (correctAnswers.get(i).equalsIgnoreCase(answers.get(i))) {
                score += scorePerQuestion;
            }
        }

        System.out.println("Score calculé: " + score);
        return score;
    }


}



