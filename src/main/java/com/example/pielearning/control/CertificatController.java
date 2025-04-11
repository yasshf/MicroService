package com.example.pielearning.control;

import com.example.pielearning.entite.Certificat;
import com.example.pielearning.service.CertificatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // Autorise Angular à accéder à l'API
@RestController
@RequestMapping("/api/certificats")
public class CertificatController {

    @Autowired
    private CertificatService certificatService;

    @GetMapping
    public List<Certificat> getAllCertificats() {
        return certificatService.getAll();
    }
    @PostMapping
    public ResponseEntity<Certificat> ajouterCertificat(@RequestBody Certificat certificat) {
        Certificat savedCertificat = certificatService.ajouterCertificat(certificat);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCertificat);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Certificat> getCertificatsParUtilisateur(@PathVariable Long utilisateurId) {
        return certificatService.getCertificatsParUtilisateur(utilisateurId);
    }

    @GetMapping("/test/{testId}")
    public List<Certificat> getCertificatsParTest(@PathVariable Long testId) {
        return certificatService.getCertificatsParTest(testId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificat> getCertificat(@PathVariable Long id) {
        Certificat certificat = certificatService.getCertificat(id);
        return ResponseEntity.ok(certificat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCertificat(@PathVariable Long id) {
        certificatService.supprimerCertificat(id);
        return ResponseEntity.noContent().build();
    }

    // Nouvelle méthode PUT pour modifier un certificat
    @PutMapping("/{id}")
    public ResponseEntity<?> modifierCertificat(@PathVariable Long id,  @RequestBody Certificat certificat, BindingResult bindingResult) {
        // Si les validations échouent
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }

        // Vérifie si le certificat existe
        Certificat existingCertificat = certificatService.getCertificat(id);
        if (existingCertificat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certificat non trouvé.");
        }

        // Mise à jour des informations du certificat
        existingCertificat.setNom(certificat.getNom());
        existingCertificat.setDateObtention(certificat.getDateObtention());
        existingCertificat.setScoreObtenu(certificat.getScoreObtenu());
        existingCertificat.setScoreMin(certificat.getScoreMin());
        existingCertificat.setTest(certificat.getTest());

        // Sauvegarde du certificat modifié
        Certificat updatedCertificat = certificatService.ajouterCertificat(existingCertificat);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCertificat);
    }
}
