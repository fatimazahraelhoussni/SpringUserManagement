package com.jpaapp.tp3springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpaapp.tp3springboot.Entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNom(String nom);
}
