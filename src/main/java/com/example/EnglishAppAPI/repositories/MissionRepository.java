package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Long, Mission> {
}
