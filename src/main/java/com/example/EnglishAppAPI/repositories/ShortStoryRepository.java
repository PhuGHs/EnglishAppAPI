package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.ShortStory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortStoryRepository extends JpaRepository<ShortStory, Long> {
    @Override
    boolean existsById(Long aLong);
    Page<ShortStory> findAll(Pageable pageable);

    @Query("SELECT s FROM ShortStory s WHERE s.id != :shortStoryId ORDER BY RAND() LIMIT 5")
    List<ShortStory> get5RandomShortStories(@Param("shortStoryId") Long shortStoryId);
}
