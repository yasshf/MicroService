package com.example.pielearning.control;
import com.example.pielearning.entite.TestResult;
import com.example.pielearning.entite.Test;
import com.example.pielearning.service.TestService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // Autorise Angular à accéder à l'API
@RestController
@RequestMapping("/api/tests")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/")
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{id}")
    public Test getTestById(@PathVariable Long id) {
        return testService.getTestById(id);
    }

    @PostMapping
    public Test createTest(@RequestBody Test test) {
        return testService.createTest(test);
    }

    @PutMapping("/{id}")
    public Test updateTest(@PathVariable Long id, @RequestBody Test testDetails) {
        return testService.updateTest(id, testDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
    }

    @PutMapping("/{id}/submit")
    public TestResult submitTest(@PathVariable Long id, @RequestBody List<String> answers) {
        Test test = testService.getTestById(id);
        // Logique pour vérifier les réponses et calculer le score
        int score = testService.calculateScore(test, answers);
        boolean passed = score >= 70;  // Seuil de réussite
        String certificateUrl = passed ? generateCertificate(test) : null;
        return new TestResult(score, passed, certificateUrl);
    }

    private String generateCertificate(Test test) {
        // Logique pour générer un certificat (peut-être un fichier PDF ou un lien)
        return "url/vers/certificat/" + test.getId();
    }
}
