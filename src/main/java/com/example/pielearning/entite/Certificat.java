package com.example.pielearning.entite;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;
import  java.io.Serializable;
import java.util.Set;
import io.swagger.v3.oas.annotations.media.Schema;
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Certificat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private LocalDate dateObtention;

    private Double scoreObtenu;

    private Double scoreMin;

    @ManyToOne
@JoinColumn(name = "utilisateur_id")
private Utilisateur utilisateur;


    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}

