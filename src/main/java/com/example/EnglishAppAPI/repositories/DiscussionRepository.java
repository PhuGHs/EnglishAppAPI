package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Discussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Page<Discussion> findAll( Pageable pageable);
    List<Discussion> findTop5ByOrderByCreatedDateDesc();

    @Override
    boolean existsById(Long aLong);
}
