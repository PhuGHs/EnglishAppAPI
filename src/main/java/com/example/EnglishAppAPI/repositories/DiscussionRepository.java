package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    List<Discussion> findAll(Pageable pageable);
    List<Discussion> findTop5ByOrderByAnswersSizeDesc();
}
