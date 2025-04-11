package com.example.pielearning.control;

import com.example.pielearning.entite.Test;
import com.example.pielearning.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class TestViewController {
    private final TestService testService;
    private static final Logger logger = LoggerFactory.getLogger(TestViewController.class);

    public TestViewController(TestService testService) {
        this.testService = testService;
    }

    // Affichage du formulaire pour ajouter un test
    @GetMapping("/form")
    public String showTestForm(Model model) {
        model.addAttribute("test", new Test());  // Ajoute un objet Test vide au modèle
        return "test-form";  // Assurez-vous que ce fichier Thymeleaf s'appelle "test-form.html"
    }

    // Enregistrement du test
    @PostMapping("/saveTest")
    public String saveTest(@ModelAttribute Test test) {
        logger.info("Tentative d'ajout d'un test: {}", test);  // Log pour vérifier les données reçues

        testService.createTest(test);  // Appelle le service pour enregistrer le test

        logger.info("Test ajouté avec succès !");
        return "redirect:/form";  // Redirige vers le formulaire après l'ajout
    }
}
