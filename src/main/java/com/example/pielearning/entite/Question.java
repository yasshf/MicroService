package com.example.pielearning.entite;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;  // Le texte de la question
    private String type;  // Par exemple "choix multiple", "vrai/faux", etc.

    @ElementCollection
    private Set<String> options;  // Liste des options pour une question à choix multiple

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;  // Relation avec le test

    @Transient
    private String correctAnswer;  // Réponse correcte, à ne pas persister en base
}
