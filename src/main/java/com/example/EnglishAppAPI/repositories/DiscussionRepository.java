package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Long, Discussion> {
}
