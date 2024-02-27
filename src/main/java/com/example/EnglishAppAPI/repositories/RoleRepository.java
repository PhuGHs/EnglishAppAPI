package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
    Boolean existsByRoleName(String roleName);
}
