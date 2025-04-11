package com.example.pielearning.service;

import com.example.pielearning.entite.Certificat;
import com.example.pielearning.entite.ResourceNotFoundException;
import com.example.pielearning.entite.Utilisateur;
import com.example.pielearning.repository.CertificatRepository;
import com.example.pielearning.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CertificatService {

    @Autowired
    private CertificatRepository certificatRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository; // Ajout du repository utilisateur
    // Ajouter un certificat
    public Certificat ajouterCertificat(Certificat certificat) {
        // Vérifier et récupérer l'utilisateur existant
        if (certificat.getUtilisateur() != null && certificat.getUtilisateur().getId() != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(certificat.getUtilisateur().getId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            certificat.setUtilisateur(utilisateur);
        }


        return certificatRepository.save(certificat);
    }
    public List<Certificat> getAll() {
        return certificatRepository.findAll(); // Récupérer tous les certificats depuis la base de données
    }

    // Récupérer tous les certificats d'un utilisateur
    public List<Certificat> getCertificatsParUtilisateur(Long utilisateurId) {
        return certificatRepository.findByUtilisateurId(utilisateurId);
    }

    // Récupérer tous les certificats d'un test
    public List<Certificat> getCertificatsParTest(Long testId) {
        return certificatRepository.findByTestId(testId);
    }

    // Récupérer un certificat spécifique
    public Certificat getCertificat(Long id) {
        return certificatRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Certificat non trouvé"));
    }

    // Supprimer un certificat
    public void supprimerCertificat(Long id) {
        certificatRepository.deleteById(id);
    }
}

