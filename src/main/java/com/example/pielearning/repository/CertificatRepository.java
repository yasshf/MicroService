package com.example.pielearning.repository;
import com.example.pielearning.entite.Certificat;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pielearning.entite.Test;
import java.util.List;
public interface CertificatRepository extends JpaRepository<Certificat, Long> {
        List<Certificat> findByUtilisateurId(Long utilisateurId);
        List<Certificat> findByTestId(Long testId);
    }




