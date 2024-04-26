package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.Answer;
import com.example.EnglishAppAPI.entities.ShortStory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortStoryRepository extends JpaRepository<ShortStory, Long> {
    @Override
    boolean existsById(Long aLong);
    Page<ShortStory> findAll(Pageable pageable);
}
