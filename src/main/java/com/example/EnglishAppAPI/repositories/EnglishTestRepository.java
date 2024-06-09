package com.example.EnglishAppAPI.repositories;

import com.example.EnglishAppAPI.entities.EnglishTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnglishTestRepository extends JpaRepository<EnglishTest, Long> {
    @Override
    boolean existsById(Long aLong);

    @Query("select t from EnglishTest t where t.englishLevel.levelId = :levelId")
    List<EnglishTest> getEnglishTest(@Param("levelId") Long levelId);
}
