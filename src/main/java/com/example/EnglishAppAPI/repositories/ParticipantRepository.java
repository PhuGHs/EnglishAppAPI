package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
