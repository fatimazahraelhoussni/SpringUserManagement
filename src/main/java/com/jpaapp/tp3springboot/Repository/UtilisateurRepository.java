package com.jpaapp.tp3springboot.Repository;

import com.jpaapp.tp3springboot.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByRoleNom(String roleNom);
}
