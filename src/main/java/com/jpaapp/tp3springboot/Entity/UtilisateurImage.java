package com.jpaapp.tp3springboot.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UtilisateurImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomImage;
    private String cheminImage;

    @OneToOne(mappedBy = "image")
    private Utilisateur utilisateur;

    // getters et setters
}
