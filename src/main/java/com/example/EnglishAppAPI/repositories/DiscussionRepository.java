package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Discussion;
import com.example.EnglishAppAPI.entities.EnglishTopic;
import com.example.EnglishAppAPI.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Page<Discussion> findAll( Pageable pageable);
    Page<Discussion> findByUser(UserEntity user, Pageable pageable);
    List<Discussion> findTop5ByOrderByCreatedDateDesc();
    Page<Discussion> findByTopic(EnglishTopic topic, Pageable pageable);
    @Override
    boolean existsById(Long aLong);
}
