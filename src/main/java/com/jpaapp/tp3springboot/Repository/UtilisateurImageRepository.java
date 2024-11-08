package com.jpaapp.tp3springboot.Repository;

import com.jpaapp.tp3springboot.Entity.Utilisateur;
import com.jpaapp.tp3springboot.Entity.UtilisateurImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurImageRepository extends JpaRepository<UtilisateurImage, Long> {
    Optional<UtilisateurImage> findByUtilisateurId(Long utilisateurId);
}