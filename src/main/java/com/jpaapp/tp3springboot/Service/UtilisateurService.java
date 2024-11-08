package com.jpaapp.tp3springboot.Service;

import com.jpaapp.tp3springboot.Entity.Utilisateur;
import com.jpaapp.tp3springboot.Entity.UtilisateurImage;
import com.jpaapp.tp3springboot.Repository.RoleRepository;
import com.jpaapp.tp3springboot.Repository.UtilisateurImageRepository;
import com.jpaapp.tp3springboot.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpaapp.tp3springboot.Entity.Role;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final UtilisateurImageRepository utilisateurImageRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, UtilisateurImageRepository utilisateurImageRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.utilisateurImageRepository = utilisateurImageRepository;
    }


    public Utilisateur creerUtilisateurAvecRole(Utilisateur utilisateur, String roleNom) {
        Optional<Role> roleOpt = roleRepository.findByNom(roleNom);
        if (roleOpt.isPresent()) {
            utilisateur.setRole(roleOpt.get());  // Utilisation de .get() pour récupérer le Role à partir de l'Optional
            return utilisateurRepository.save(utilisateur);
        }
        return null;
    }



    public Utilisateur ajouterImageAUtilisateur(Long utilisateurId, UtilisateurImage image) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        if (utilisateur != null) {
            image.setUtilisateur(utilisateur);
            utilisateur.setImage(image);
            utilisateurImageRepository.save(image);
            return utilisateurRepository.save(utilisateur);
        }
        return null;
    }


    public List<Utilisateur> recupererUtilisateursParRole(String roleNom) {
        return utilisateurRepository.findByRoleNom(roleNom);
    }
}
