package com.jpaapp.tp3springboot.Controller;

import com.jpaapp.tp3springboot.Entity.Role;
import com.jpaapp.tp3springboot.Entity.Utilisateur;
import com.jpaapp.tp3springboot.Entity.UtilisateurImage;
import com.jpaapp.tp3springboot.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // Récupérer tous les Utilisateurs
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.recupererUtilisateursParRole(null);  // Si aucun rôle n'est précisé, on récupère tous les utilisateurs
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }

    // Créer un nouvel Utilisateur
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        // On suppose que le rôle est déjà présent dans l'objet Utilisateur
        Utilisateur nouvelUtilisateur = utilisateurService.creerUtilisateurAvecRole(utilisateur, utilisateur.getRole().getNom());
        if (nouvelUtilisateur != null) {
            return new ResponseEntity<>(nouvelUtilisateur, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Récupérer un Utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.recupererUtilisateursParRole(null).stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (utilisateur != null) {
            return new ResponseEntity<>(utilisateur, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Associer un rôle à un Utilisateur
    @PutMapping("/{utilisateurId}/role/{roleId}")
    public ResponseEntity<Utilisateur> assignRoleToUtilisateur(@PathVariable Long utilisateurId, @PathVariable Long roleId) {
        // On suppose que le rôle est déjà récupéré et que l'utilisateur existe
        Utilisateur utilisateur = utilisateurService.recupererUtilisateursParRole(null).stream()
                .filter(u -> u.getId().equals(utilisateurId))
                .findFirst()
                .orElse(null);
        if (utilisateur != null) {

            Role role = new Role(roleId, "Rôle Test", null);  // Cette ligne doit être remplacée par l'appel à RoleRepository pour obtenir un rôle réel
            utilisateur.setRole(role);
            utilisateurService.creerUtilisateurAvecRole(utilisateur, role.getNom());
            return new ResponseEntity<>(utilisateur, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Ajouter une image à un Utilisateur
    @PostMapping("/{utilisateurId}/image")
    public ResponseEntity<Utilisateur> addImageToUtilisateur(@PathVariable Long utilisateurId, @RequestBody UtilisateurImage image) {
        Utilisateur utilisateur = utilisateurService.ajouterImageAUtilisateur(utilisateurId, image);
        if (utilisateur != null) {
            return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Suppression d'un Utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.recupererUtilisateursParRole(null).stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (utilisateur != null) {
            utilisateurService.recupererUtilisateursParRole(null).remove(utilisateur);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Suppression d'un Rôle par ID
    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        Role role = new Role(roleId, "Rôle Test", null);  // Cette ligne doit être remplacée par l'appel à RoleRepository pour obtenir un rôle réel
        if (role != null) {
            // La logique de suppression du rôle serait ici
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Suppression d'une image d'un Utilisateur
    @DeleteMapping("/{utilisateurId}/image/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long utilisateurId, @PathVariable Long imageId) {
        Utilisateur utilisateur = utilisateurService.recupererUtilisateursParRole(null).stream()
                .filter(u -> u.getId().equals(utilisateurId))
                .findFirst()
                .orElse(null);
        if (utilisateur != null && utilisateur.getImage() != null && utilisateur.getImage().getId().equals(imageId)) {
            utilisateur.setImage(null);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
