package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishTestRepository extends JpaRepository<EnglishTest, Long> {
    @Override
    boolean existsById(Long aLong);
}
